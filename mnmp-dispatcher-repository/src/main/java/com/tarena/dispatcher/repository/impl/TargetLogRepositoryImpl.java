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

package com.tarena.dispatcher.repository.impl;

import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.bo.PhoneBizIdReceiptBO;
import com.tarena.dispatcher.event.SmsNoticeEvent;
import com.tarena.dispatcher.respository.TargetLogRepository;
import com.tarena.dispatcher.storage.mapper.NoticeSmsRecordTargetDao;
import com.tarena.mnmp.domain.NoticeSmsRecordTargetDO;
import com.tarena.mnmp.enums.Provider;
import com.tarena.mnmp.enums.TargetStatus;
import com.tarena.mnmp.protocol.NoticeEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TargetLogRepositoryImpl implements TargetLogRepository {

    @Autowired
    private NoticeSmsRecordTargetDao recordTargetDao;

    @Override public TargetStatus getSmsStatus(NoticeEvent noticeEvent, String target) {
        Integer status = recordTargetDao.getStatusByTaskAndTriggerTime(noticeEvent.getTaskId(), noticeEvent.getTriggerTime(), target);
        if (status != null) {
            return TargetStatus.getStatusEnum(status);
        }
        return null;
    }

    @Override public void newSuccessSmsTarget(SmsNoticeEvent event, SmsTarget target, TargetStatus targetStatus,
        String bizId) {
        addSmsTargetByTask(event, target, targetStatus.status(), bizId, "SUCCESS");
    }

    @Override public void newFailSmsTarget(SmsNoticeEvent event, SmsTarget target, TargetStatus targetStatus,
        String errorMsg) {
        addSmsTargetByTask(event, target, targetStatus.status(), null, errorMsg);
    }

    @Override public List<PhoneBizIdReceiptBO> queryNotReceiptBizIds(Provider provider) {
        List<NoticeSmsRecordTargetDO> targets = this.recordTargetDao.queryNotReceiptBizIds();
        List<PhoneBizIdReceiptBO> phoneBizIdPairs = new ArrayList<>(targets.size());
        for (NoticeSmsRecordTargetDO target : targets) {
            PhoneBizIdReceiptBO phoneBizIdPair = new PhoneBizIdReceiptBO(target.getTarget(), target.getBizId(), target.getTaskId());
            phoneBizIdPairs.add(phoneBizIdPair);
        }
        return phoneBizIdPairs;
    }

    @Override public void modifyTargetReceiptStatus(Provider provider, List<PhoneBizIdReceiptBO> receiptedList) {
        recordTargetDao.batchUpdate(receiptedList);
    }

    private void addSmsTargetByTask(SmsNoticeEvent event, SmsTarget target, Integer targetStatus,
        String bizId, String resultMsg) {
        Date current = new Date();
        NoticeSmsRecordTargetDO noticeSmsRecordTarget = new NoticeSmsRecordTargetDO();
        noticeSmsRecordTarget.setTaskId(event.getNoticeEvent().getTaskId());
        noticeSmsRecordTarget.setTarget(target.getTarget());
        noticeSmsRecordTarget.setTriggerTime(event.getNoticeEvent().getTriggerTime());
        noticeSmsRecordTarget.setPushTime(current);
        noticeSmsRecordTarget.setCreateTime(current);
        noticeSmsRecordTarget.setUpdateTime(current);
        noticeSmsRecordTarget.setContent(target.getContent());
        noticeSmsRecordTarget.setStatus(targetStatus);
        noticeSmsRecordTarget.setBizId(bizId);
        noticeSmsRecordTarget.setAppCode(target.getAppCode());
        noticeSmsRecordTarget.setSendResult(resultMsg);
        recordTargetDao.insert(noticeSmsRecordTarget);
    }
}
