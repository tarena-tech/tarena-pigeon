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

import com.alibaba.fastjson.JSON;
import com.tarena.mnmp.commons.mq.EventHandlerMappingContainer;
import com.tarena.mnmp.commons.mq.MQClient;
import com.tarena.mnmp.commons.mq.MQContainerProvider;
import com.tarena.mnmp.commons.mq.MQEvent;
import com.tarena.mnmp.commons.mq.MQHandler;
import com.tarena.mnmp.commons.mq.MQIdempotent;
import java.util.List;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocketMQMessageListener implements MessageListenerConcurrently {
    private static Logger logger = LoggerFactory.getLogger(RocketMQMessageListener.class);

    public RocketMQMessageListener() {
        logger.info("init spring rocket mq message listener");
    }

    private EventHandlerMappingContainer queueHandlerMappingContainer = MQContainerProvider.getContainer();
    private MessageConverter messageConverter;

    private MQIdempotent mqIdempotent;

    public void setQueueHandlerMappingContainer(EventHandlerMappingContainer queueHandlerMappingContainer) {
        this.queueHandlerMappingContainer = queueHandlerMappingContainer;
    }

    public void setMqIdempotent(MQIdempotent mqIdempotent) {
        this.mqIdempotent = mqIdempotent;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    protected boolean duplicate(MQEvent event, String consumerKey, String keys) {
        return mqIdempotent != null && mqIdempotent.duplicate(keys);
    }

    protected void consumed(MQEvent event, String consumerKey, String keys) {
        //must be idempotent
        if (mqIdempotent == null) {
            return;
        }
        //must be consume successful
        if (!mqIdempotent.consumed(keys)) {
            //如果未消费成功，说明断网，或者已经被消费过
            return;
        }
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
        System.err.println(String.format("thread-name:%s,message-size:%s", Thread.currentThread().getName(), list.size()));
        logger.info("thread-name:{},message-size:{}", Thread.currentThread().getName(), list.size());
        for (MessageExt message : list) {
            String type = message.getProperties().get(MQClient.CLASS_NAME);
            try {
                if (logger.isInfoEnabled()) {
                    logger.info("receive msg:" + message.toString());
                }
                logger.info("................. type:{}", type);
                MQHandler handler = queueHandlerMappingContainer.get(type);
                if (handler == null) {
                    logger.info("handler of this type [{}] not found", type);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                MQEvent event = messageConverter.fromMessage(message);
                String consumerKey = message.getProperties().get(MQClient.CONSUMER_KEY);
                if (this.duplicate(event, consumerKey, message.getKeys())) {
                    logger.info("已消费过,重复信息无需处理,data: {}", JSON.toJSONString(event));
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                logger.info("mq send message:{}", JSON.toJSONString(event));
                handler.handle(event);
                this.consumed(event, consumerKey, message.getKeys());
            } catch (Throwable e) {
                logger.error("process failed, msg : " + message, e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
