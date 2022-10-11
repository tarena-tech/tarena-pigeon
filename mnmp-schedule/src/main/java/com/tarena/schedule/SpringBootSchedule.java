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

import com.alibaba.fastjson.JSON;
import com.tarena.dispatcher.NoticeEventGetter;
import com.tarena.mnmp.commons.mq.MQPublisher;
import com.tarena.mnmp.constant.AlarmKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringBootSchedule extends AbstractScheduler {

    @Autowired
    private MQPublisher mqPublisher;

    private static Logger logger = LoggerFactory.getLogger(SpringBootSchedule.class);

    @Override boolean stop() {
        return false;
    }

    @Override <T extends NoticeEventGetter> void send(T event) {
        try {
            if (event == null) {
                logger.error("send event is null");
                return;
            }
            this.mqPublisher.publish(event);
        } catch (Throwable e) {
            logger.error("发送mq失败, data:{}", JSON.toJSONString(event));
            this.monitor.alarms(AlarmKey.MSG_SENT_ERROR, event.getNoticeEvent().toString());
        }
    }
}
