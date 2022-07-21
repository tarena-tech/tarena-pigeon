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

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.tarena.dispatcher.assemble.impl.EmailTargetAssembler;
import com.tarena.dispatcher.assemble.impl.SmsTargetAssembler;
import com.tarena.dispatcher.impl.EmailAliNoticeDispatcher;
import com.tarena.dispatcher.impl.SmsAliNoticeDispatcher;
import com.tarena.dispatcher.properties.DispatcherConfig;
import com.tarena.dispatcher.respository.ProviderRepository;
import com.tarena.dispatcher.respository.TargetLogRepository;
import com.tarena.mnmp.commons.json.Json;
import com.tarena.mnmp.commons.utils.DollarPlaceholderReplacer;
import com.tarena.mnmp.protocol.ProviderClientConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DispatcherConfig.class)
public class DispatcherConfiguration {

    private DispatcherConfig dispatcherConfig;

    public DispatcherConfiguration(DispatcherConfig dispatcherConfig) {
        this.dispatcherConfig = dispatcherConfig;
    }

    @Bean
    @ConditionalOnMissingBean(DollarPlaceholderReplacer.class)
    public DollarPlaceholderReplacer dollarPlaceholderReplacer() {
        return new DollarPlaceholderReplacer();
    }

    @Bean
    @ConditionalOnMissingBean(EmailTargetAssembler.class)
    @ConditionalOnProperty(prefix = "dispatcher", value = "assembler_email", havingValue = "true")
    public EmailTargetAssembler emailTargetAssembler(DollarPlaceholderReplacer dollarPlaceholderReplacer) {
        EmailTargetAssembler emailTargetAssembler = new EmailTargetAssembler();
        emailTargetAssembler.setDollarPlaceholderReplacer(dollarPlaceholderReplacer);
        return emailTargetAssembler;
    }

    @Bean
    @ConditionalOnMissingBean(SmsTargetAssembler.class)
    @ConditionalOnProperty(prefix = "dispatcher", value = "assembler_sms", havingValue = "true")
    public SmsTargetAssembler smsTargetAssembler(DollarPlaceholderReplacer dollarPlaceholderReplacer) {
        SmsTargetAssembler smsTargetAssembler = new SmsTargetAssembler();
        smsTargetAssembler.setDollarPlaceholderReplacer(dollarPlaceholderReplacer);
        return smsTargetAssembler;
    }

    @Bean
    @ConditionalOnProperty(prefix = "dispatcher", value = "sms_ali_pigeon_provider_code")
    @ConditionalOnBean({ProviderRepository.class})
    public Client smsAliClient(ProviderRepository providerRepository) throws Exception {
        ProviderClientConfig providerClientConfig = providerRepository.getClientConfig(this.dispatcherConfig.getSmsAliPigeonProviderCode());
        Config config = new Config();
        config.setAccessKeyId(providerClientConfig.getAccessKeyId());
        config.setAccessKeySecret(providerClientConfig.getAccessKeySecret());
        return new Client(config);
    }

    @Bean
    @ConditionalOnMissingBean(SmsAliNoticeDispatcher.class)
    @ConditionalOnBean({TargetLogRepository.class, ProviderRepository.class})
    @ConditionalOnProperty(prefix = "dispatcher", value = "notice_sms_ali", havingValue = "true")
    public SmsAliNoticeDispatcher smsAliNoticeDispatcher(Json json, TargetLogRepository targetLogRepository,
        ProviderRepository providerRepository, Client smsAliClient) {
        ProviderClientConfig providerClientConfig = providerRepository.getClientConfig(this.dispatcherConfig.getSmsAliPigeonProviderCode());
        SmsAliNoticeDispatcher aliNoticeDispatcher = new SmsAliNoticeDispatcher();
        aliNoticeDispatcher.setJsonProvider(json);
        aliNoticeDispatcher.setTargetLogRepository(targetLogRepository);
        aliNoticeDispatcher.setAliTemplateCode(providerClientConfig.getDefaultTemplate());
        aliNoticeDispatcher.setAliSmsClient(smsAliClient);
        aliNoticeDispatcher.setMock(this.dispatcherConfig.getMock());
        return aliNoticeDispatcher;
    }

    @Bean
    @ConditionalOnMissingBean(EmailAliNoticeDispatcher.class)
    @ConditionalOnBean({TargetLogRepository.class})
    @ConditionalOnProperty(prefix = "dispatcher", value = "notice_email_ali", havingValue = "true")
    public EmailAliNoticeDispatcher emailAliNoticeDispatcher(Json json, TargetLogRepository targetLogRepository) {
        EmailAliNoticeDispatcher emailAliNoticeDispatcher = new EmailAliNoticeDispatcher();
        emailAliNoticeDispatcher.setJsonProvider(json);
        emailAliNoticeDispatcher.setTargetLogRepository(targetLogRepository);
        return emailAliNoticeDispatcher;
    }
}
