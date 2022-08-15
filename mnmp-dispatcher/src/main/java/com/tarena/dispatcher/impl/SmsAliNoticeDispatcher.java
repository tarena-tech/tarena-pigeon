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

import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.bo.PhoneBizIdReceiptBO;
import com.tarena.dispatcher.event.SmsNoticeEvent;
import com.tarena.dispatcher.sender.SmsSender;
import com.tarena.mnmp.enums.Provider;
import com.tarena.mnmp.enums.TargetStatus;
import com.tarena.mnmp.protocol.NoticeEvent;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class SmsAliNoticeDispatcher extends AbstractNoticeDispatcher<SmsNoticeEvent> {
    private static Logger logger = LoggerFactory.getLogger(SmsAliNoticeDispatcher.class);
    
    private Boolean receipt = true;

    private SmsSender smsSender;

    public void setSmsSender(SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void setReceipt(Boolean receipt) {
        this.receipt = receipt;
    }

    public String getNoticeType() {
        return Provider.ALI_SMS.name();
    }

    protected String doDispatcher(SmsNoticeEvent notice, SmsTarget smsTarget) throws Exception {
        if (notice.getNoticeEvent().getMock().equals(1)) {
//            int delayTime = new Random().nextInt(200);
//            Thread.sleep(delayTime);
            return "delayTime" + "-" + System.currentTimeMillis();
        }
        return this.smsSender.send(smsTarget);
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

    private void doReceipt() {
        try {
            List<PhoneBizIdReceiptBO> bizIds = targetLogRepository.queryNotReceiptBizIds(Provider.ALI_SMS);
            if (CollectionUtils.isEmpty(bizIds)) {
                //空则延迟1000ms
                Thread.sleep(1000);
                return;
            }
            List<PhoneBizIdReceiptBO> receiptedList = this.smsSender.fetchReceipt(bizIds);
            targetLogRepository.modifyTargetReceiptStatus(Provider.ALI_SMS, receiptedList);
            Thread.sleep(10);
        } catch (Throwable e) {
            logger.error("ali sms receipt error", e);
        }
    }

    public void receipt() {
        if (!this.receipt) {
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
