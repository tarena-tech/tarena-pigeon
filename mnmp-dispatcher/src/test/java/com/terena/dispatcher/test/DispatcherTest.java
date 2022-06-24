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

package com.terena.dispatcher.test;

import com.terena.dispatcher.BaseNoticeTarget;
import com.terena.dispatcher.impl.DispatcherRegistry;
import com.terena.dispatcher.impl.EmailAliNoticeDispatcher;
import com.terena.dispatcher.impl.SmsAliNoticeDispatcher;
import com.terena.mnmp.commons.enums.NoticeType;

public class DispatcherTest {
    public static void main(String[] args) throws Exception {
        EmailAliNoticeDispatcher emailAliNoticeDispatcher=new EmailAliNoticeDispatcher();
        emailAliNoticeDispatcher.afterPropertiesSet();
        SmsAliNoticeDispatcher smsAliNoticeDispatcher=new SmsAliNoticeDispatcher();
        smsAliNoticeDispatcher.afterPropertiesSet();
        BaseNoticeTarget notice=new BaseNoticeTarget();
        notice.setNoticeType(NoticeType.EMAIL.name());
        notice.setProviderCode("Ali");
        DispatcherRegistry.getInstance().dispatcher(notice);
    }
}
