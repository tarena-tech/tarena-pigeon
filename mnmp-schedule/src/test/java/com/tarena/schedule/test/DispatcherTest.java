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

package com.tarena.schedule.test;

import com.tarena.dispatcher.assemble.impl.TargetAssemblerRegistry;
import com.tarena.dispatcher.event.SmsNoticeEvent;
import com.tarena.dispatcher.impl.DispatcherRegistry;
import com.tarena.mnmp.api.NoticeDTO;
import com.tarena.mnmp.api.TargetDTO;
import com.tarena.mnmp.enums.NoticeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DispatcherTest {
    private static SmsNoticeEvent assemble() throws Exception {
        NoticeDTO notice = new NoticeDTO();
        notice.setNoticeType(NoticeType.SMS);
        notice.setSignName("阿里云测试短信");
        notice.setTemplateCode("SMS_154950909");
        notice.setTemplateContent("1656");
        List<String> strings = Arrays.asList("18510273063");
        Map<String, Object> params = new HashMap<>();
        params.put("code", "1656");
        List<TargetDTO> targetDTOS = new ArrayList<>();
        for (String string : strings) {
            TargetDTO targetDTO = new TargetDTO(string, params);
            targetDTOS.add(targetDTO);
        }
        notice.setTargets(targetDTOS);
        SmsNoticeEvent targetList = TargetAssemblerRegistry.getInstance().assemble(notice, 1);
        return targetList;
    }

    @Test
    public void assembler() throws Exception {
        assemble();
    }



    @Test
    public void test() throws Exception {
        SmsNoticeEvent baseNoticeTargets = assemble();
        DispatcherRegistry.getInstance().dispatcher(baseNoticeTargets);
    }
}
