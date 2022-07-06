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

import com.tarena.dispatcher.NoticeEvent;
import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.event.SmsNoticeEvent;
import com.tarena.dispatcher.respository.TargetLogRepository;
import com.tarena.mnmp.enums.NoticeType;
import com.tarena.mnmp.enums.TargetStatus;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsAliNoticeDispatcher extends AbstractNoticeDispatcher<SmsNoticeEvent> {
    private static Logger logger = LoggerFactory.getLogger(SmsAliNoticeDispatcher.class);

    private TargetLogRepository targetLogRepository;

    public String getNoticeType() {
        return NoticeType.SMS.name().toLowerCase() + "Ali";
    }

    private void doDispatcher(SmsNoticeEvent notice, SmsTarget target) {
        try {
            //TODO DO dispatcher
            this.targetLogRepository.newSmsTargetLog(notice, target, TargetStatus.SENT_TO_PROVIDER);
        } catch (Exception e) {
            logger.error("sms sent fail notice:{} target:{}", this.jsonProvider.toString(notice), this.jsonProvider.toString(target), e);
            this.targetLogRepository.newSmsTargetLog(notice, target, TargetStatus.SENT_FAIL);
        }
    }

    @Override
    public void dispatcher(SmsNoticeEvent notice) {
        List<SmsTarget> targets = notice.getTargets();
        NoticeEvent event = notice.getNoticeEvent();
        for (SmsTarget smsTarget : targets) {
            TargetStatus sentStatus = this.targetLogRepository.getSmsStatus(event, smsTarget.getTarget());
            //未发送或发送失败则重试
            if (sentStatus == null || sentStatus.equals(TargetStatus.UNSENT) || sentStatus.equals(TargetStatus.SENT_FAIL)) {
                this.doDispatcher(notice, smsTarget);
            }
            logger.info("sms-ali dispatcher " + this.jsonProvider.toString(smsTarget));
        }
    }
}
