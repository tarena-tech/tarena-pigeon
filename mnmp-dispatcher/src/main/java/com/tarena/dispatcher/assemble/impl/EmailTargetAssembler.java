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
import com.tarena.dispatcher.EmailTarget;
import com.tarena.dispatcher.event.EmailNoticeEvent;
import com.tarena.mnmp.api.NoticeDTO;
import com.tarena.mnmp.commons.enums.NoticeType;
import java.util.ArrayList;
import java.util.List;

public class EmailTargetAssembler extends AbstractTargetAssembler<EmailNoticeEvent> {
    @Override
    public String getNoticeType() {
        return NoticeType.EMAIL.toString();
    }

    @Override
    public EmailNoticeEvent assemble(NoticeDTO notice) {
        EmailNoticeEvent emailNoticeWap = new EmailNoticeEvent();
        DefaultNoticeEvent noticeEvent = new DefaultNoticeEvent(notice.getTaskId(), notice.getTriggerTime(), notice.getNoticeType(), "Ali");
        emailNoticeWap.setNoticeEvent(noticeEvent);
        List<String> targets = notice.getTargets();
        List<EmailTarget> targetList = new ArrayList<>();
        for (String target : targets) {
            EmailTarget emailTarget = new EmailTarget();
            emailTarget.setTarget(target);
            targetList.add(emailTarget);
        }
        emailNoticeWap.setTargets(targetList);
        return emailNoticeWap;
    }
}
