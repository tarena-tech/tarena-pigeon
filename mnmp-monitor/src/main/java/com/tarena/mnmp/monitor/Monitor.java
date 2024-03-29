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

package com.tarena.mnmp.monitor;

import com.tarena.mnmp.api.NoticeDTO;
import com.tarena.mnmp.enums.TargetStatus;
import com.tarena.mnmp.protocol.NoticeEvent;

public interface Monitor {
    default void alarms(String key, String msg) {

    }

    /**
     * 监控前端请求
     *
     * @param notice
     */
    default void noticeRequest(NoticeDTO notice) {

    }

    /**
     * 监控通知目标状态
     *
     * @param noticeEvent
     * @param target
     * @param status
     */
    default void noticeStatus(NoticeEvent noticeEvent, String target, TargetStatus status) {

    }

    /**
     * 监控schedule 调度
     */
    default void schedule() {

    }
}
