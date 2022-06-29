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

package com.tarena.schedule;

import com.tarena.dispatcher.NoticeEventGetter;
import com.tarena.dispatcher.assemble.impl.TargetAssemblerRegistry;
import com.tarena.mnmp.api.NoticeDTO;
import com.tarena.mnmp.commons.enums.NoticeType;
import com.tarena.schedule.utils.NoticeTaskTrigger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public abstract class AbstractScheduler {

    private static Logger logger = LoggerFactory.getLogger(AbstractScheduler.class);

    abstract List<NoticeTaskTrigger> queryTriggers();

    abstract boolean stop();

    abstract void finishTask(NoticeTaskTrigger noticeTaskTrigger);

    abstract void updateNextTriggerTime(NoticeTaskTrigger noticeTaskTrigger);

    abstract List<List<String>> getTargets(NoticeTaskTrigger noticeTaskTrigger);

    abstract <T extends NoticeEventGetter> void send(T noticeEvent);

    private List<NoticeDTO> assemble(NoticeTaskTrigger noticeTaskTrigger, List<List<String>> targets) {
        List<NoticeDTO> notices = new ArrayList<>();
        for (List<String> targetList : targets) {
            NoticeDTO notice = new NoticeDTO();
            notice.setNoticeType(NoticeType.SMS);
            notice.setTargets(targetList);
            //todo assemble notice
            notices.add(notice);
        }
        return notices;
    }

    public void schedule() {
        //while (!stop()) {
        try {
            List<NoticeTaskTrigger> triggers = this.queryTriggers();
            for (NoticeTaskTrigger trigger : triggers) {
                List<List<String>> targetsList = this.getTargets(trigger);
                List<NoticeDTO> notices = this.assemble(trigger, targetsList);
                for (NoticeDTO notice : notices) {
                    this.send(TargetAssemblerRegistry.getInstance().assemble(notice));
                }
                Date nextTriggerTime = trigger.generateNextTriggerTime();
                if (trigger.isFinish(nextTriggerTime)) {
                    this.finishTask(trigger);
                }
            }
            if (CollectionUtils.isEmpty(triggers)) {
                Thread.sleep(1000L);
            }
        } catch (Throwable e) {
            logger.error("schedule error ", e);
        }
        //}
    }
}