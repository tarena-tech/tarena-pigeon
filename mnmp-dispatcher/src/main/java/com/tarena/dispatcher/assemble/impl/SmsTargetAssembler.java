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

package com.tarena.dispatcher.assemble.impl;

import com.tarena.dispatcher.DefaultNoticeEvent;
import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.event.SmsNoticeEvent;
import com.tarena.mnmp.api.NoticeDTO;
import com.tarena.mnmp.api.TargetDTO;
import com.tarena.mnmp.enums.NoticeType;
import java.util.ArrayList;
import java.util.List;

public class SmsTargetAssembler extends AbstractTargetAssembler<SmsNoticeEvent> {
    @Override
    public String getNoticeType() {
        return NoticeType.SMS.toString();
    }



    @Override
    public SmsNoticeEvent assemble(NoticeDTO notice, Integer batchIndex) {
        SmsNoticeEvent smsNoticeEvent = new SmsNoticeEvent();
        DefaultNoticeEvent noticeEvent = new DefaultNoticeEvent(notice.getTaskId(), notice.getTriggerTime(), notice.getNoticeType(), "Ali", batchIndex, notice.getTargets().size());
        smsNoticeEvent.setNoticeEvent(noticeEvent);
        List<TargetDTO> targets = notice.getTargets();
        List<SmsTarget> targetList = new ArrayList<>();
        for (TargetDTO targetDto : targets) {
            SmsTarget smsTarget = new SmsTarget();
            smsTarget.setTarget(targetDto.getTarget());
            smsTarget.setSignName(notice.getSignName());
            smsTarget.setTemplateCode(notice.getTemplateCode());
            smsTarget.setTemplateParam("{\"code\":\"" + notice.getTemplateContent() + "\"}");
            smsTarget.setContent(this.dollarPlaceholderReplacer.buildContent(notice.getTemplateCode(),targetDto.getParams()));
            targetList.add(smsTarget);
        }
        smsNoticeEvent.setTargets(targetList);
        return smsNoticeEvent;
    }
}
