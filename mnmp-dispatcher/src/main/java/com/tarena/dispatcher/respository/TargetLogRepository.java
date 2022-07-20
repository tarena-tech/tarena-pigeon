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

package com.tarena.dispatcher.respository;

import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.bo.PhoneBizIdReceiptBO;
import com.tarena.dispatcher.event.SmsNoticeEvent;
import com.tarena.mnmp.enums.Provider;
import com.tarena.mnmp.enums.TargetStatus;
import com.tarena.mnmp.protocol.NoticeEvent;
import java.util.List;

public interface TargetLogRepository {

    TargetStatus getSmsStatus(NoticeEvent noticeEvent, String target);

    Integer modifySmsStatus(NoticeEvent noticeEvent, String target, TargetStatus targetStatus);

    void newSuccessSmsTarget(SmsNoticeEvent event, SmsTarget target, TargetStatus targetStatus, String bizId);

    void newFailSmsTarget(SmsNoticeEvent event, SmsTarget target, TargetStatus targetStatus, String errorMsg);

    List<PhoneBizIdReceiptBO> queryNotReceiptBizIds(Provider provider);

    void modifyTargetReceiptStatus(Provider provider,List<PhoneBizIdReceiptBO> receiptedList);

}
