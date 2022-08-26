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


package com.tarena.mnmp.mq.rocketmq.configurations;

import com.tarena.mnmp.commons.mq.MQPublisher;
import com.tarena.mnmp.mq.rocketmq.JsonMessageConverter;
import com.tarena.mnmp.mq.rocketmq.MessageConverter;
import com.tarena.mnmp.mq.rocketmq.RocketMQPublisher;
import com.tarena.mnmp.mq.rocketmq.configurations.properties.RocketMqConfig;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RocketMqConfig.class)
public class RocketMqPublisherConfiguration {
    private RocketMqConfig rocketMqConfig;

    public RocketMqPublisherConfiguration(RocketMqConfig rocketMqConfig) {
        this.rocketMqConfig = rocketMqConfig;
    }

    @Bean
    @ConditionalOnMissingBean(MessageConverter.class)
    @ConditionalOnProperty(prefix = "rocket", value = "message_charset")
    public MessageConverter messageConverter() {
        JsonMessageConverter jsonMessageConverter = new JsonMessageConverter();
        jsonMessageConverter.setCharset(this.rocketMqConfig.getMessageCharset());
        return jsonMessageConverter;
    }

    @Bean
    @ConditionalOnMissingBean(MQPublisher.class)
    @ConditionalOnProperty(prefix = "rocket", value = "publish_group")
    public MQPublisher publisher(MessageConverter messageConverter) throws MQClientException {
        RocketMQPublisher publisher = new RocketMQPublisher();
        publisher.setTopic(this.rocketMqConfig.getTopic());
        publisher.setNameServerAddress(this.rocketMqConfig.getNameServerAddress());
        publisher.setGroup(this.rocketMqConfig.getPublishGroup());
        publisher.setMessageConverter(messageConverter);
        publisher.setRetryTimesWhenSendAsyncFailed(this.rocketMqConfig.getRetryTimesWhenSendFailed());
        publisher.setDebug(this.rocketMqConfig.getPublishDebug());
        publisher.start();
        return publisher;
    }
}
