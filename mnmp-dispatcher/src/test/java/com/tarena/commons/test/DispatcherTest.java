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

package com.tarena.commons.test;

import com.tarena.dispatcher.assemble.impl.EmailTargetAssembler;
import com.tarena.dispatcher.assemble.impl.SmsTargetAssembler;
import com.tarena.dispatcher.assemble.impl.TargetAssemblerRegistry;
import com.tarena.dispatcher.event.SmsNoticeEvent;
import com.tarena.dispatcher.impl.DispatcherRegistry;
import com.tarena.dispatcher.impl.EmailAliNoticeDispatcher;
import com.tarena.dispatcher.impl.SmsAliNoticeDispatcher;
import com.tarena.mnmp.api.NoticeDTO;
import com.tarena.mnmp.enums.NoticeType;
import java.util.Arrays;
import org.junit.Test;

public class DispatcherTest {

    private static SmsNoticeEvent assemble() throws Exception {
        EmailTargetAssembler emailTargetAssembler = new EmailTargetAssembler();
        emailTargetAssembler.afterPropertiesSet();
        SmsTargetAssembler smsTargetAssembler = new SmsTargetAssembler();
        smsTargetAssembler.afterPropertiesSet();

        NoticeDTO notice = new NoticeDTO();
        notice.setNoticeType(NoticeType.SMS);
        notice.setTemplateParam("t1");
        notice.setTargets(Arrays.asList("1", "2", "3", "4"));
        SmsNoticeEvent targetList = TargetAssemblerRegistry.getInstance().assemble(notice, 1);
        //String json = SerializationProvider.get().serialize(TargetAssemblerRegistry.getInstance().assemble(notice));
        //List<SmsTarget> targetList = SerializationProvider.get().deserialize(json, SmsTarget.class);
        //Assert.assertEquals(5, targetList.size());
        return targetList;
    }

    @Test
    public void assembler() throws Exception {
        assemble();
    }

    @Test
    public void test() throws Exception {
        EmailAliNoticeDispatcher emailAliNoticeDispatcher = new EmailAliNoticeDispatcher();
        emailAliNoticeDispatcher.afterPropertiesSet();
        SmsAliNoticeDispatcher smsAliNoticeDispatcher = new SmsAliNoticeDispatcher();
        smsAliNoticeDispatcher.afterPropertiesSet();
        SmsNoticeEvent baseNoticeTargets = assemble();
        DispatcherRegistry.getInstance().dispatcher(baseNoticeTargets);
    }
}
