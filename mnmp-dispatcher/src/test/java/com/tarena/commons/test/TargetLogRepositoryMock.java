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

package com.tarena.commons.test;

import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.bo.PhoneBizIdReceiptBO;
import com.tarena.dispatcher.event.SmsNoticeEvent;
import com.tarena.dispatcher.respository.TargetLogRepository;
import com.tarena.mnmp.enums.Provider;
import com.tarena.mnmp.enums.TargetStatus;
import com.tarena.mnmp.protocol.NoticeEvent;
import java.util.List;

public class TargetLogRepositoryMock implements TargetLogRepository {
    @Override public TargetStatus getSmsStatus(NoticeEvent noticeEvent, String target) {
        return null;
    }

    @Override public Integer modifySmsStatus(NoticeEvent noticeEvent, String target, TargetStatus targetStatus) {
        return null;
    }

    @Override public void newSuccessSmsTarget(SmsNoticeEvent event, SmsTarget target, TargetStatus targetStatus,
        String bizId) {

    }

    @Override public void newFailSmsTarget(SmsNoticeEvent event, SmsTarget target, TargetStatus targetStatus,
        String errorMsg) {

    }

    @Override public List<PhoneBizIdReceiptBO> queryNotReceiptBizIds(Provider provider) {
        return null;
    }

    @Override public void modifyTargetReceiptStatus(Provider provider, List<PhoneBizIdReceiptBO> receiptedList) {

    }
}
