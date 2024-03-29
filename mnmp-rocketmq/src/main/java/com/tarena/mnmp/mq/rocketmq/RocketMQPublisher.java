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

package com.tarena.mnmp.mq.rocketmq;

import com.tarena.mnmp.commons.mq.MQClient;
import com.tarena.mnmp.commons.mq.MQEvent;
import com.tarena.mnmp.commons.mq.MQMessageSendException;
import com.tarena.mnmp.commons.mq.MQPublisher;
import java.util.Collections;
import java.util.UUID;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocketMQPublisher implements MQPublisher {
    protected static Logger logger = LoggerFactory.getLogger(RocketMQPublisher.class);
    private String nameServerAddress;
    private String group;
    private String topic;
    private Boolean debug;
    private MQProducer producer;
    private MessageConverter messageConverter;
    private Integer retryTimesWhenSendAsyncFailed = 5;

    public String getNameServerAddress() {
        return nameServerAddress;
    }

    public void setNameServerAddress(String nameServerAddress) {
        this.nameServerAddress = nameServerAddress;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTopic() {
        return topic;
    }


    public MessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public Integer getRetryTimesWhenSendAsyncFailed() {
        return retryTimesWhenSendAsyncFailed;
    }

    public void setRetryTimesWhenSendAsyncFailed(Integer retryTimesWhenSendAsyncFailed) {
        this.retryTimesWhenSendAsyncFailed = retryTimesWhenSendAsyncFailed;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    @Override
    public void publish(MQEvent event) {
        Message msg = this.messageConverter.createMessage(topic,"",event);
        String key = UUID.randomUUID().toString();
        msg.setKeys(Collections.singletonList(key));
        // logger.info("event {} ,monitor key {},msgKey {}", JsonFactory.getProvider().toString(event), productKey == null ? "" : productKey.key(), key);
        SendResult sendResult = null;
        int retryTimes = 0;
        while (retryTimes < retryTimesWhenSendAsyncFailed) {
            retryTimes++;
            if (retryTimes > 2) {
                logger.warn("event {} retry times {}", event, retryTimes);
            }
            try {
                sendResult = producer.send(msg);
                if (!sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                    throw new MQMessageSendException(sendResult.toString());
                }
                break;
            } catch (Throwable e) {
                logger.warn(e.getClass().getSimpleName() + " retry", e);
                if (retryTimes == retryTimesWhenSendAsyncFailed - 1) {
                    throw new MQMessageSendException("client exception", e);
                }
            }
        }
    }

    public void start() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr(nameServerAddress);
        producer.setInstanceName(MQClient.INSTANCE_NAME);
        //for product
        if (this.debug == null || !this.debug) {
            producer.setCreateTopicKey(this.getTopic());
        }

        int maxMessageSize = 1024000;
        producer.setMaxMessageSize(maxMessageSize);
        this.producer = producer;
        producer.start();
    }
}
