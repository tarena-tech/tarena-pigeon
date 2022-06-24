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

package com.terena.dispatcher.impl;

import com.terena.dispatcher.NoticeDispatcher;
import com.terena.mnmp.api.NoticeTargetEvent;
import com.terena.mnmp.commons.constant.ErrorCode;
import com.terena.mnmp.commons.protocol.BusinessException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherRegistry {
    private DispatcherRegistry() {
    }

    static class Nested {
        private static DispatcherRegistry dispatcherRegistry = new DispatcherRegistry();
    }

    public static DispatcherRegistry getInstance() {
        return Nested.dispatcherRegistry;
    }

    private Map<String, NoticeDispatcher> noticeDispatcherContainer = new HashMap<String, NoticeDispatcher>();

    public void put(NoticeDispatcher noticeDispatcher) {
        String noticeType = noticeDispatcher.getNoticeType();
        this.noticeDispatcherContainer.put(noticeType, noticeDispatcher);
    }

    public <T extends NoticeTargetEvent> void dispatcher(T notice) throws BusinessException {

        NoticeDispatcher noticeDispatcher = this.noticeDispatcherContainer.get(notice.getNoticeType().toLowerCase() + notice.getProvider());
        if (noticeDispatcher == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Notice Dispatcher not found. notice type=" + notice.getNoticeType() + "provider =" + notice.getProvider());
        }
    }

    public <T extends NoticeTargetEvent> void dispatcher(List<T> notices) throws BusinessException {
        for (NoticeTargetEvent noticeEvent : notices) {
            this.dispatcher(noticeEvent);
        }
    }
}
