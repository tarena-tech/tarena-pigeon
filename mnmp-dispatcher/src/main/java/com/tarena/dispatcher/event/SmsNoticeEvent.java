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

package com.tarena.dispatcher.event;

import com.tarena.dispatcher.DefaultNoticeEvent;
import com.tarena.dispatcher.NoticeEventGetter;
import com.tarena.dispatcher.SmsTarget;
import com.tarena.mnmp.protocol.NoticeEvent;
import java.util.List;

public class SmsNoticeEvent implements NoticeEventGetter {
    private DefaultNoticeEvent noticeEvent;

    private List<SmsTarget> targets;

    public void setNoticeEvent(DefaultNoticeEvent noticeEvent) {
        this.noticeEvent = noticeEvent;
    }

    public List<SmsTarget> getTargets() {
        return targets;
    }

    public void setTargets(List<SmsTarget> targets) {
        this.targets = targets;
    }

    @Override public NoticeEvent getNoticeEvent() {
        return noticeEvent;
    }
}
