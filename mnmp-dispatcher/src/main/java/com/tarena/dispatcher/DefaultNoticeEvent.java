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

package com.tarena.dispatcher;

import com.tarena.mnmp.enums.NoticeType;
import com.tarena.mnmp.protocol.NoticeEvent;

public class DefaultNoticeEvent implements NoticeEvent {
    private Long taskId;
    private String triggerTime;
    private NoticeType noticeType;
    private String provider;
    private Integer batch;
    private Integer size;

    public DefaultNoticeEvent(Long taskId, String triggerTime, NoticeType noticeType, String provider, Integer batch,
        Integer size) {
        this.taskId = taskId;
        this.triggerTime = triggerTime;
        this.noticeType = noticeType;
        this.provider = provider;
        this.batch = batch;
        this.size = size;
    }

    public Integer batchIndex() {
        return this.batch;
    }

    public Integer getSize() {
        return this.size;
    }

    @Override public Long getTaskId() {
        return this.taskId;
    }

    @Override public String getTriggerTime() {
        return this.triggerTime;
    }

    @Override public NoticeType getNoticeType() {
        return this.noticeType;
    }

    @Override public String getProvider() {
        return this.provider;
    }

    @Override public String toString() {
        return "{" +
            "taskId=" + taskId +
            ", triggerTime='" + triggerTime + '\'' +
            ", noticeType=" + noticeType +
            ", provider='" + provider + '\'' +
            ", batch=" + batch +
            ", size=" + size +
            '}';
    }
}
