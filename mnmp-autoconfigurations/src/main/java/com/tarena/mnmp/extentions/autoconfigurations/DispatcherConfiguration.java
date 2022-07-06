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

package com.tarena.mnmp.extentions.autoconfigurations;

import com.tarena.dispatcher.assemble.impl.EmailTargetAssembler;
import com.tarena.dispatcher.assemble.impl.SmsTargetAssembler;
import com.tarena.dispatcher.impl.EmailAliNoticeDispatcher;
import com.tarena.dispatcher.impl.SmsAliNoticeDispatcher;
import com.tarena.dispatcher.respository.TargetLogRepository;
import com.tarena.mnmp.commons.json.Json;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DispatcherConfiguration {

    @Bean
    @ConditionalOnMissingBean(EmailTargetAssembler.class)
    @ConditionalOnProperty(prefix = "dispatcher", value = "assembler_email", havingValue = "true")
    public EmailTargetAssembler emailTargetAssembler() {
        return new EmailTargetAssembler();
    }

    @Bean
    @ConditionalOnMissingBean(SmsTargetAssembler.class)
    @ConditionalOnProperty(prefix = "dispatcher", value = "assembler_sms", havingValue = "true")
    public SmsTargetAssembler smsTargetAssembler() {
        return new SmsTargetAssembler();
    }

    @Bean
    @ConditionalOnMissingBean(SmsAliNoticeDispatcher.class)
    @ConditionalOnProperty(prefix = "dispatcher", value = "notice_sms_ali", havingValue = "true")
    public SmsAliNoticeDispatcher smsAliNoticeDispatcher(Json json, TargetLogRepository targetLogRepository) {
        SmsAliNoticeDispatcher aliNoticeDispatcher = new SmsAliNoticeDispatcher();
        aliNoticeDispatcher.setJsonProvider(json);
        aliNoticeDispatcher.setTargetLogRepository(targetLogRepository);
        return aliNoticeDispatcher;
    }

    @Bean
    @ConditionalOnMissingBean(SmsAliNoticeDispatcher.class)
    @ConditionalOnProperty(prefix = "dispatcher", value = "notice_email_ali", havingValue = "true")
    public EmailAliNoticeDispatcher emailAliNoticeDispatcher(Json json, TargetLogRepository targetLogRepository) {
        EmailAliNoticeDispatcher emailAliNoticeDispatcher = new EmailAliNoticeDispatcher();
        emailAliNoticeDispatcher.setJsonProvider(json);
        emailAliNoticeDispatcher.setTargetLogRepository(targetLogRepository);
        return emailAliNoticeDispatcher;
    }
}
