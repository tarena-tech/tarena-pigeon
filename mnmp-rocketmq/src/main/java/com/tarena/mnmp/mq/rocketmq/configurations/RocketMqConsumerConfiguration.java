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

import com.tarena.mnmp.commons.mq.MQContainerProvider;
import com.tarena.mnmp.constant.Pair;
import com.tarena.mnmp.constant.Symbol;
import com.tarena.mnmp.mq.rocketmq.JsonMessageConverter;
import com.tarena.mnmp.mq.rocketmq.MessageConverter;
import com.tarena.mnmp.mq.rocketmq.RocketMQConsumer;
import com.tarena.mnmp.mq.rocketmq.RocketMQMessageListener;
import com.tarena.mnmp.mq.rocketmq.TopicTagPair;
import com.tarena.mnmp.mq.rocketmq.configurations.properties.RocketMqConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UnknownFormatConversionException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RocketMqConfig.class)
public class RocketMqConsumerConfiguration {

    private RocketMqConfig rocketMqConfig;

    public RocketMqConsumerConfiguration(RocketMqConfig rocketMqConfig) {
        this.rocketMqConfig = rocketMqConfig;
    }

    @Bean
    public MessageConverter messageConverter() {
        JsonMessageConverter jsonMessageConverter = new JsonMessageConverter();
        jsonMessageConverter.setCharset(this.rocketMqConfig.getMessageCharset());
        return jsonMessageConverter;
    }

    @Bean
    public RocketMQMessageListener rocketMQMessageListener(MessageConverter messageConverter) {
        RocketMQMessageListener rocketMQMessageListener = new RocketMQMessageListener();
        rocketMQMessageListener.setMessageConverter(messageConverter);
        rocketMQMessageListener.setQueueHandlerMappingContainer(MQContainerProvider.getContainer());
        return rocketMQMessageListener;
    }

    @Bean
    public RocketMQConsumer rocketMQConsumer(RocketMQMessageListener rocketMQMessageListener) {

        List<TopicTagPair> topicConfigList = new ArrayList<TopicTagPair>();
        if (!this.rocketMqConfig.getSubscribeTopicTags().contains(Symbol.COLON)) {
            throw new UnknownFormatConversionException("format error for example topic1:tag1,tag1,tag3|topic2:tag1,tag2,tag3");
        }
        String[] topicArray = this.rocketMqConfig.getSubscribeTopicTags().split("\\|");
        for (String topic : topicArray) {
            TopicTagPair topicTagPair = new TopicTagPair();
            Pair<String, String> topicTagsPair = Pair.split(topic, Symbol.COLON);
            topicTagPair.setTopic(topicTagsPair.getFirst());
            String[] tagArray = topicTagsPair.getSecond().split(",");
            topicTagPair.setTags(Arrays.asList(tagArray));
            topicConfigList.add(topicTagPair);
        }
        RocketMQConsumer rocketMQConsumer = new RocketMQConsumer();
        rocketMQConsumer.setGroupName(this.rocketMqConfig.getConsumerGroup());
        rocketMQConsumer.setNameServerAddress(this.rocketMqConfig.getNameServerAddress());
        rocketMQConsumer.setTopicConfigList(topicConfigList);
        rocketMQConsumer.setMessageListener(rocketMQMessageListener);
        rocketMQConsumer.setThreadCount(rocketMqConfig.getConsumerThreadCount());
        rocketMQConsumer.start();
        return rocketMQConsumer;
    }
}
