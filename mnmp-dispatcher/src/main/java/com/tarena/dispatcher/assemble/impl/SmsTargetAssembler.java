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

import com.tarena.dispatcher.SmsNoticeTarget;
import com.tarena.mnmp.api.NoticeDTO;
import com.tarena.mnmp.commons.enums.NoticeType;
import java.util.ArrayList;
import java.util.List;

public class SmsTargetAssembler extends AbstractTargetAssembler<SmsNoticeTarget> {
    @Override
    public String getNoticeType() {
        return NoticeType.SMS.toString();
    }

    @Override
    public List<SmsNoticeTarget> assemble(NoticeDTO notice) {
        List<SmsNoticeTarget> smsNoticeTargets = new ArrayList<>();
        String targets = notice.getTargets();
        String[] targetArray = targets.split(",");
        for (String target : targetArray) {
            SmsNoticeTarget noticeTarget = new SmsNoticeTarget();
            noticeTarget.setSignName("阿里云短信测试");
            noticeTarget.setTemplateCode("SMS_154950909");
            noticeTarget.setTemplateParam("{\"code\":\""+notice.getTemplateParam()+"\"}");
            noticeTarget.setTarget(target);
            noticeTarget.setNoticeType(this.getNoticeType());
            noticeTarget.setProviderCode("Ali");
            smsNoticeTargets.add(noticeTarget);
        }
        return smsNoticeTargets;
    }
}
