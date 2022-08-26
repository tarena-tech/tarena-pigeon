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
package com.tarena.mnmp.mq.rocketmq.configurations.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rocket")
public class RocketMqConfig {
    /**
     * nameServerAddress
     * <p>
     * ${rocket.name_server_address}
     */
    private String nameServerAddress;

    /**
     * ${rocket.message_charset}
     */
    private String messageCharset;

    /**
     * is enable publish debug
     */
    private Boolean publishDebug;

    /**
     * rocket mq topic
     */
    private String topic;

    /**
     * publish group
     */
    private String publishGroup;

    private Integer retryTimesWhenSendFailed;

    /**
     * consumer group
     */
    private String consumerGroup;
    /**
     * topic:tag,tag|tapic2:tag,tag
     */
    private String subscribeTopicTags;

    /**
     * consumer thread count
     */
    private Integer consumerThreadCount;

    public String getNameServerAddress() {
        return nameServerAddress;
    }

    public void setNameServerAddress(String nameServerAddress) {
        this.nameServerAddress = nameServerAddress;
    }

    public String getMessageCharset() {
        return messageCharset;
    }

    public void setMessageCharset(String messageCharset) {
        this.messageCharset = messageCharset;
    }

    public Boolean getPublishDebug() {
        return publishDebug;
    }

    public void setPublishDebug(Boolean publishDebug) {
        this.publishDebug = publishDebug;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getRetryTimesWhenSendFailed() {
        return retryTimesWhenSendFailed;
    }

    public void setRetryTimesWhenSendFailed(Integer retryTimesWhenSendFailed) {
        this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
    }

    public String getSubscribeTopicTags() {
        return subscribeTopicTags;
    }

    public void setSubscribeTopicTags(String subscribeTopicTags) {
        this.subscribeTopicTags = subscribeTopicTags;
    }

    public String getPublishGroup() {
        return publishGroup;
    }

    public void setPublishGroup(String publishGroup) {
        this.publishGroup = publishGroup;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public Integer getConsumerThreadCount() {
        return consumerThreadCount;
    }

    public void setConsumerThreadCount(Integer consumerThreadCount) {
        this.consumerThreadCount = consumerThreadCount;
    }
}