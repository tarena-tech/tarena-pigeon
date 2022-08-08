/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tarena.dispatcher.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.QuerySendDetailsRequest;
import com.aliyun.dysmsapi20170525.models.QuerySendDetailsResponse;
import com.aliyun.dysmsapi20170525.models.QuerySendDetailsResponseBody;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.bo.PhoneBizIdReceiptBO;
import com.tarena.dispatcher.event.SmsNoticeEvent;
import com.tarena.mnmp.commons.utils.SmsUtils;
import com.tarena.mnmp.constant.Constant;
import com.tarena.mnmp.constant.ErrorCode;
import com.tarena.mnmp.enums.NoticeType;
import com.tarena.mnmp.enums.Provider;
import com.tarena.mnmp.enums.TargetStatus;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.NoticeEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class SmsAliNoticeDispatcher extends AbstractNoticeDispatcher<SmsNoticeEvent> {
    private static Logger logger = LoggerFactory.getLogger(SmsAliNoticeDispatcher.class);
    private String aliTemplateCode;
    private Client aliSmsClient;
    private Boolean receipt = false;

    public void setAliTemplateCode(String aliTemplateCode) {
        this.aliTemplateCode = aliTemplateCode;
    }

    public void setAliSmsClient(Client aliSmsClient) {
        this.aliSmsClient = aliSmsClient;
    }

    public String getNoticeType() {
        return NoticeType.SMS.name().toLowerCase() + "Ali";
    }

    protected String doDispatcher(SmsNoticeEvent notice, SmsTarget smsTarget) throws Exception {
        if (notice.getNoticeEvent().getMock().equals(1)) {
            int delayTime = new Random().nextInt(200);
            Thread.sleep(delayTime);
            return delayTime + "-" + System.currentTimeMillis();
        }
        SendSmsRequest sendReq = new SendSmsRequest()
            .setPhoneNumbers(smsTarget.getTarget())
            //"阿里云短信测试"
            .setSignName(smsTarget.getSignName())
            .setTemplateCode(this.aliTemplateCode)
            .setTemplateParam("{\"content\":\"" + smsTarget.getContent() + "\"}");
        SendSmsResponse sendResp = this.aliSmsClient.sendSms(sendReq);
        if (!Constant.OK.equals(sendResp.body.code)) {
            logger.error("错误信息: " + sendResp.body.message + "");
            throw new BusinessException(ErrorCode.SEND_ALI_SMS_ERROR, sendResp.body.message);
        }
        return sendResp.body.bizId;
    }

    @Override
    public void dispatcher(SmsNoticeEvent notice) {
        List<SmsTarget> targets = notice.getTargets();
        NoticeEvent event = notice.getNoticeEvent();
        for (SmsTarget smsTarget : targets) {
            //todo 升级redis 或hbase 提高并发吞吐
            TargetStatus sentStatus = this.targetLogRepository.getSmsStatus(event, smsTarget.getTarget());
            //未发送或发送失败则重试
            if (sentStatus == null || sentStatus.equals(TargetStatus.SENT_FAIL)) {
                try {
                    String bizId = this.doDispatcher(notice, smsTarget);
                    this.targetLogRepository.newSuccessSmsTarget(notice, smsTarget, TargetStatus.SENT_TO_PROVIDER, bizId);
                    this.monitor.noticeStatus(event, smsTarget.getTarget(), TargetStatus.SENT_TO_PROVIDER);
                } catch (Exception e) {
                    logger.error("sms sent fail notice:{} target:{}", this.jsonProvider.toString(notice), this.jsonProvider.toString(smsTarget), e);
                    this.targetLogRepository.newFailSmsTarget(notice, smsTarget, TargetStatus.SENT_FAIL, e.getMessage());
                    this.monitor.noticeStatus(event, smsTarget.getTarget(), TargetStatus.SENT_FAIL);
                }
            }
        }
    }

    /**
     * ali yun sms 参考文档 https://next.api.aliyun.com/document/Dysmsapi/2017-05-25/QuerySendDetails
     *
     * @param receipts
     * @return
     */
    private List<PhoneBizIdReceiptBO> fetchReceipt(List<PhoneBizIdReceiptBO> receipts) {
        LinkedHashSet<Long> taskIds = new LinkedHashSet<>(receipts.size());
        for (PhoneBizIdReceiptBO phoneBizIdReceipt : receipts) {
            taskIds.add(phoneBizIdReceipt.getTaskId());
        }
        Map<Long, Integer> taskMockStatusMap = this.taskRepository.queryTaskMockStatus(taskIds);

        List<PhoneBizIdReceiptBO> receiptedList = new ArrayList<>(receipts.size());
        for (PhoneBizIdReceiptBO phoneReceipt : receipts) {
            Date current = new Date();
            Integer mockStatus = taskMockStatusMap.get(phoneReceipt.getTaskId());
            //mock数据
            if (mockStatus.equals(1)) {
                phoneReceipt.setSuccess(true);
                phoneReceipt.setSendTime(current);
                phoneReceipt.setReceiveDate(current);
                phoneReceipt.setCostNumbers(1);
                receiptedList.add(phoneReceipt);
                continue;
            }

            try {
                QuerySendDetailsRequest querySendDetailsRequest = new QuerySendDetailsRequest();
                querySendDetailsRequest.setBizId(phoneReceipt.getBizId());
                querySendDetailsRequest.setPhoneNumber(phoneReceipt.getPhone());
                querySendDetailsRequest.setCurrentPage(1L);
                querySendDetailsRequest.setPageSize(1L);
                querySendDetailsRequest.setSendDate(DateFormatUtils.format(new Date(), Constant.DATE_FORMAT_YYYYMMDD));
                QuerySendDetailsResponse querySendDetailsResponse =
                    this.aliSmsClient.querySendDetails(querySendDetailsRequest);
                QuerySendDetailsResponseBody querySendDetailsResponseBody = querySendDetailsResponse.getBody();
                if (Constant.OK.equals(querySendDetailsResponseBody.getCode())) {
                    QuerySendDetailsResponseBody.QuerySendDetailsResponseBodySmsSendDetailDTOs querySendDetailsResponseBodySmsSendDetailDTOs = querySendDetailsResponseBody.getSmsSendDetailDTOs();
                    List<QuerySendDetailsResponseBody.QuerySendDetailsResponseBodySmsSendDetailDTOsSmsSendDetailDTO> details = querySendDetailsResponseBodySmsSendDetailDTOs.getSmsSendDetailDTO();
                    for (QuerySendDetailsResponseBody.QuerySendDetailsResponseBodySmsSendDetailDTOsSmsSendDetailDTO detail : details) {
                        /**
                         * 短信发送状态，包括：
                         *
                         * 1：等待回执。
                         * 2：发送失败。
                         * 3：发送成功
                         */
                        if (detail.getSendStatus().equals(3L)) {
                            phoneReceipt.setSuccess(true);
                            if (StringUtils.isNotBlank(detail.getSendDate())) {
                                phoneReceipt.setSendTime(DateUtils.parseDate(detail.getSendDate(), Constant.DATE_FORMAT_SECOND));
                            }
                            if (StringUtils.isNotBlank(detail.getReceiveDate())) {
                                phoneReceipt.setReceiveDate(DateUtils.parseDate(detail.getReceiveDate(), Constant.DATE_FORMAT_SECOND));
                            }
                            phoneReceipt.setCostNumbers(SmsUtils.smsCostCount(detail.getContent().length()));
                            receiptedList.add(phoneReceipt);
                        } else if (detail.getSendStatus().equals(2L)) {
                            phoneReceipt.setSuccess(false);
                            receiptedList.add(phoneReceipt);
                        } else {
                            logger.error(" receipting {}", phoneReceipt.getPhone());
                        }
                    }
                }
                return receiptedList;
            } catch (Exception e) {
                logger.error("NoticeServiceImpl insertAliTargetData is error; recordId:{} phone:{}", phoneReceipt.getBizId(), phoneReceipt.getPhone(), e);
            }
        }
        return receiptedList;
    }

    private void doReceipt() {
        try {
            List<PhoneBizIdReceiptBO> bizIds = targetLogRepository.queryNotReceiptBizIds(Provider.ALI_SMS);
            if (CollectionUtils.isEmpty(bizIds)) {
                //空则延迟100ms
                Thread.sleep(100);
                return;
            }
            List<PhoneBizIdReceiptBO> receiptedList = this.fetchReceipt(bizIds);
            targetLogRepository.modifyTargetReceiptStatus(Provider.ALI_SMS, receiptedList);
        } catch (Throwable e) {
            logger.error("ali sms receipt error", e);
        }
    }

    public void receipt() {
        if (this.receipt) {
            return;
        }
        ExecutorService receiptService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(1),
            new BasicThreadFactory.Builder()
                .namingPattern("ali-sms-receipt-service-%d").daemon(true).build());
        receiptService.submit(new Runnable() {
            @Override public void run() {
                while (true) {
                    SmsAliNoticeDispatcher.this.doReceipt();
                }
            }
        });
    }

    @Override public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        this.receipt();
    }
}
