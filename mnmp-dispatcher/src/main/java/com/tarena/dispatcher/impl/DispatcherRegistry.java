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

package com.tarena.dispatcher.impl;

import com.tarena.dispatcher.NoticeDispatcher;
import com.tarena.dispatcher.NoticeEventGetter;
import com.tarena.mnmp.constant.ErrorCode;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.NoticeEvent;
import java.util.HashMap;
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

    public <T extends NoticeEventGetter> void dispatcher(T noticeEventWrap) throws BusinessException {
        NoticeEvent noticeEvent = noticeEventWrap.getNoticeEvent();
        String dispatcherKey = noticeEvent.getProvider();
        NoticeDispatcher noticeDispatcher = this.noticeDispatcherContainer.get(dispatcherKey);
        if (noticeDispatcher == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Notice Dispatcher not found. notice type=" + noticeEvent.getNoticeType().name() + "provider =" + noticeEvent.getProvider());
        }
        noticeDispatcher.dispatcher(noticeEventWrap);
    }
}
