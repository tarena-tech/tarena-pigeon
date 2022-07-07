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
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.event.SmsNoticeEvent;
import com.tarena.mnmp.constant.ErrorCode;
import com.tarena.mnmp.enums.NoticeType;
import com.tarena.mnmp.enums.TargetStatus;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.NoticeEvent;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsAliNoticeDispatcher extends AbstractNoticeDispatcher<SmsNoticeEvent> {
    private static Logger logger = LoggerFactory.getLogger(SmsAliNoticeDispatcher.class);

    private static final String OK = "OK";

    private String aliTemplateCode;

    public void setAliTemplateCode(String aliTemplateCode) {
        this.aliTemplateCode = aliTemplateCode;
    }

    private Client aliSmsClient;

    public void setAliSmsClient(Client aliSmsClient) {
        this.aliSmsClient = aliSmsClient;
    }

    public String getNoticeType() {
        return NoticeType.SMS.name().toLowerCase() + "Ali";
    }

    protected String doDispatcher(SmsNoticeEvent notice, SmsTarget smsTarget) throws Exception {
        SendSmsRequest sendReq = new SendSmsRequest()
            .setPhoneNumbers(smsTarget.getTarget())
            .setSignName(smsTarget.getSignName())
            .setTemplateCode(this.aliTemplateCode)
            .setTemplateParam("{\"content\":\"" + smsTarget.getContent() + "\"}");
        SendSmsResponse sendResp = this.aliSmsClient.sendSms(sendReq);
        if (!OK.equals(sendResp.body.code)) {
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
            TargetStatus sentStatus = this.targetLogRepository.getSmsStatus(event, smsTarget.getTarget());
            //未发送或发送失败则重试
            if (sentStatus == null || sentStatus.equals(TargetStatus.UNSENT) || sentStatus.equals(TargetStatus.SENT_FAIL)) {
                try {
                    String bizId = this.doDispatcher(notice, smsTarget);
                    this.targetLogRepository.newSuccessSmsTargetLog(notice, smsTarget, TargetStatus.SENT_FAIL, bizId);
                    this.monitor.noticeStatus(event, smsTarget.getTarget(), TargetStatus.SENT_TO_PROVIDER);
                } catch (Exception e) {
                    logger.error("sms sent fail notice:{} target:{}", this.jsonProvider.toString(notice), this.jsonProvider.toString(smsTarget), e);
                    this.targetLogRepository.newFailSmsTargetLog(notice, smsTarget, TargetStatus.SENT_FAIL, e.getMessage());
                    this.monitor.noticeStatus(event, smsTarget.getTarget(), TargetStatus.SENT_FAIL);
                }
            }
        }
    }
}
