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
package com.tarena.dispatcher.sender;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.QuerySendDetailsRequest;
import com.aliyun.dysmsapi20170525.models.QuerySendDetailsResponse;
import com.aliyun.dysmsapi20170525.models.QuerySendDetailsResponseBody;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.bo.PhoneBizIdReceiptBO;
import com.tarena.dispatcher.respository.TaskRepository;
import com.tarena.mnmp.commons.utils.SmsUtils;
import com.tarena.mnmp.constant.Constant;
import com.tarena.mnmp.constant.ErrorCode;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AliSmsSender implements SmsSender {

    private static Logger logger = LoggerFactory.getLogger(AliSmsSender.class);

    private String aliTemplateCode;
    protected TaskRepository taskRepository;
    private Client aliSmsClient;

    public void setAliTemplateCode(String aliTemplateCode) {
        this.aliTemplateCode = aliTemplateCode;
    }

    public void setAliSmsClient(Client aliSmsClient) {
        this.aliSmsClient = aliSmsClient;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * ali yun sms 参考文档 https://next.api.aliyun.com/document/Dysmsapi/2017-05-25/QuerySendDetails
     *
     * @param receipts
     * @return
     */
    public List<PhoneBizIdReceiptBO> fetchReceipt(List<PhoneBizIdReceiptBO> receipts) {
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

    @Override public String send(SmsTarget smsTarget) throws Exception {
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
}
