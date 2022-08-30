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

import com.alibaba.fastjson.JSONArray;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.tarena.dispatcher.assemble.impl.EmailTargetAssembler;
import com.tarena.dispatcher.assemble.impl.SmsTargetAssembler;
import com.tarena.dispatcher.impl.EmailAliNoticeDispatcher;
import com.tarena.dispatcher.impl.HuanWeiSmsClient;
import com.tarena.dispatcher.impl.SmsAliNoticeDispatcher;
import com.tarena.dispatcher.impl.SmsHwNoticeDispatcher;
import com.tarena.dispatcher.properties.DispatcherConfig;
import com.tarena.dispatcher.properties.LimitProperties;
import com.tarena.dispatcher.respository.ProviderRepository;
import com.tarena.dispatcher.respository.TargetLogRepository;
import com.tarena.dispatcher.respository.TaskRepository;
import com.tarena.dispatcher.sender.AliSmsSender;
import com.tarena.dispatcher.sender.HuaWeiSender;
import com.tarena.dispatcher.sender.SmsSender;
import com.tarena.mnmp.commons.json.Json;
import com.tarena.mnmp.commons.utils.DollarPlaceholderReplacer;
import com.tarena.mnmp.enums.Provider;
import com.tarena.mnmp.monitor.Monitor;
import com.tarena.mnmp.protocol.ProviderClientConfig;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(DispatcherConfig.class)
public class DispatcherConfiguration {

    private DispatcherConfig dispatcherConfig;

    public DispatcherConfiguration(DispatcherConfig dispatcherConfig) {
        this.dispatcherConfig = dispatcherConfig;
    }

    @Bean
    public LimitProperties limitProperties() {
        return new LimitProperties();
    }

    @Bean
    @ConditionalOnMissingBean({RestOperations.class, RestTemplate.class})
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(3000);
        factory.setConnectTimeout(3000);
        RestTemplate restTemplate = new RestTemplate(factory);
        MappingJackson2HttpMessageConverter wxMessageConverter = new MappingJackson2HttpMessageConverter();
        wxMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
        restTemplate.getMessageConverters().add(wxMessageConverter);
        return restTemplate;
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
        smsTargetAssembler.contentConvertPut(Provider.ALI_SMS.name(), c -> c);
        smsTargetAssembler.contentConvertPut(Provider.HW_SMS.name(), s -> {
            JSONArray array = new JSONArray(Collections.singletonList(s));
            return array.toJSONString();
        });
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
    @ConditionalOnProperty(prefix = "dispatcher", value = "sms_hw_pigeon_provider_code")
    @ConditionalOnBean({ProviderRepository.class})
    public HuanWeiSmsClient smsHwClient(ProviderRepository providerRepository, RestTemplate restTemplate) {
        ProviderClientConfig providerClientConfig = providerRepository.getClientConfig(this.dispatcherConfig.getSmsHwPigeonProviderCode());
        return new HuanWeiSmsClient(providerClientConfig.getAccessKeyId(), providerClientConfig.getAccessKeySecret(), restTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(SmsAliNoticeDispatcher.class)
    @ConditionalOnClass({TargetLogRepository.class, ProviderRepository.class, SmsSender.class})
    @ConditionalOnProperty(prefix = "dispatcher", value = "notice_sms_ali", havingValue = "true")
    public SmsAliNoticeDispatcher smsAliNoticeDispatcher(Json json, TargetLogRepository targetLogRepository,
        Monitor monitor, SmsSender aliSmsSender) {
        SmsAliNoticeDispatcher aliNoticeDispatcher = new SmsAliNoticeDispatcher();
        aliNoticeDispatcher.setJsonProvider(json);
        aliNoticeDispatcher.setTargetLogRepository(targetLogRepository);
        aliNoticeDispatcher.setSmsSender(aliSmsSender);
        aliNoticeDispatcher.setMonitor(monitor);
        if (null != this.dispatcherConfig.getEnableReceipt()) {
            aliNoticeDispatcher.setReceipt(this.dispatcherConfig.getEnableReceipt());
        }
        return aliNoticeDispatcher;
    }

    @Bean
    @ConditionalOnMissingBean(SmsHwNoticeDispatcher.class)
    @ConditionalOnClass({TargetLogRepository.class, ProviderRepository.class, SmsSender.class})
    @ConditionalOnProperty(prefix = "dispatcher", value = "notice_sms_hw", havingValue = "true")
    public SmsHwNoticeDispatcher smsHwNoticeDispatcher(Json json, TargetLogRepository targetLogRepository,
        Monitor monitor, SmsSender hwSmsSender) {
        SmsHwNoticeDispatcher hwNoticeDispatcher = new SmsHwNoticeDispatcher();
        hwNoticeDispatcher.setJsonProvider(json);
        hwNoticeDispatcher.setTargetLogRepository(targetLogRepository);
        hwNoticeDispatcher.setSmsSender(hwSmsSender);
        hwNoticeDispatcher.setMonitor(monitor);
        if (null != this.dispatcherConfig.getEnableReceipt()) {
            hwNoticeDispatcher.setReceipt(this.dispatcherConfig.getEnableReceipt());
        }
        return hwNoticeDispatcher;
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

    @Bean("aliSmsSender")
    @ConditionalOnMissingBean(AliSmsSender.class)
    @ConditionalOnBean({Client.class, TaskRepository.class, ProviderRepository.class})
    public SmsSender aliSmsSender(Client smsAliClient, TaskRepository taskRepository,
        ProviderRepository providerRepository) {
        ProviderClientConfig providerClientConfig = providerRepository.getClientConfig(this.dispatcherConfig.getSmsAliPigeonProviderCode());
        AliSmsSender sender = new AliSmsSender();
        sender.setAliTemplateCode(providerClientConfig.getDefaultTemplate());
        sender.setAliSmsClient(smsAliClient);
        sender.setTaskRepository(taskRepository);
        return sender;
    }

    @Bean("hwSmsSender")
    @ConditionalOnMissingBean(HuaWeiSender.class)
    @ConditionalOnBean({HuanWeiSmsClient.class, TaskRepository.class, ProviderRepository.class})
    public SmsSender hwSmsSender(HuanWeiSmsClient huanWeiSmsClient, TaskRepository taskRepository,
        ProviderRepository providerRepository) {
        ProviderClientConfig providerClientConfig = providerRepository.getClientConfig(this.dispatcherConfig.getSmsHwPigeonProviderCode());
        HuaWeiSender sender = new HuaWeiSender();
        sender.setHwTemplateCode(providerClientConfig.getDefaultTemplate());
        sender.setHuanWeiSmsClient(huanWeiSmsClient);
        sender.setTaskRepository(taskRepository);
        return sender;
    }

}
