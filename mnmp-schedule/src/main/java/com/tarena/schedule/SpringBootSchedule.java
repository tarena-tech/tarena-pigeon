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
import com.tarena.mnmp.commons.mq.MQPublisher;
import com.tarena.mnmp.enums.CycleLevel;
import com.tarena.mnmp.enums.SendType;
import com.tarena.schedule.utils.NoticeTaskTrigger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringBootSchedule extends AbstractScheduler {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private MQPublisher mqPublisher;

    @Override List<NoticeTaskTrigger> queryTriggers() {
        List<NoticeTaskTrigger> triggers = new ArrayList<>();
        NoticeTaskTrigger trigger = new NoticeTaskTrigger();
        trigger.setCycleLevel(CycleLevel.HOUR.getLevel());
        trigger.setNextTriggerTime(new Date());
        trigger.setTriggerEndTime(new Date());
        trigger.setSendType(SendType.DELAY);
        trigger.setCycleNum(1);
        triggers.add(trigger);
        return triggers;
    }

    @Override boolean stop() {
        return false;
    }

    @Override void finishTask(NoticeTaskTrigger noticeTaskTrigger) {

    }

    @Override void updateNextTriggerTime(NoticeTaskTrigger noticeTaskTrigger) {

    }

    @Override List<List<String>> getTargets(NoticeTaskTrigger noticeTaskTrigger) {
        List<List<String>> targetsList = new ArrayList<>();
        List<String> targets = new ArrayList<>();
        targets.add("135");
        targets.add("136");
        targets.add("137");
        targetsList.add(targets);
        return targetsList;
    }

    @Override <T extends NoticeEventGetter> void send(T event) {
        this.mqPublisher.publish(event);
    }
}
