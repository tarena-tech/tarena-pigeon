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

package com.tarena.mnmp.mq.rocketmq.test;

import com.tarena.mnmp.commons.mq.DefaultQueueHandlerMappingContainer;
import com.tarena.mnmp.commons.mq.EventHandlerMappingContainer;
import com.tarena.mnmp.constant.Constant;
import com.tarena.mnmp.mq.rocketmq.JsonMessageConverter;
import com.tarena.mnmp.mq.rocketmq.RocketMQConsumer;
import com.tarena.mnmp.mq.rocketmq.RocketMQMessageListener;
import com.tarena.mnmp.mq.rocketmq.RocketMQPublisher;
import com.tarena.mnmp.mq.rocketmq.TopicTagPair;
import com.tarena.mnmp.mq.rocketmq.test.protocol.event.HelloEvent;
import com.tarena.mnmp.mq.rocketmq.test.protocol.handler.HelloWorldHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class PublisherTest {

    @Test
    public void test() throws Exception {
        /**
         * <bean id="mqPublisher" class="com.sparrow.rocketmq.spring.SpringRocketMQPublisherImpl">
         *         <property name="messageConverter" ref="messageConverter"/>
         *         <property name="topic" value="sparrow-topic"/>
         *         <property name="group" value="sender-group"/>
         *         <property name="nameServerAddress" value="127.0.0.1:9876"/>
         *         <property name="debug" value="true"/>
         *     </bean>
         */
        JsonMessageConverter messageConverter = new JsonMessageConverter();
        messageConverter.setCharset(Constant.CHARSET_UTF_8);
        RocketMQPublisher publisher = new RocketMQPublisher();
        publisher.setDebug(true);
        publisher.setTopic("test-topic");
        publisher.setGroup("sender-group");
        publisher.setNameServerAddress("127.0.0.1:9876");
        publisher.setMessageConverter(messageConverter);

        EventHandlerMappingContainer eventHandlerMappingContainer = new DefaultQueueHandlerMappingContainer();
        HelloWorldHandler helloWorldHandler = new HelloWorldHandler();
        helloWorldHandler.afterPropertiesSet();
        publisher.start();

        RocketMQMessageListener rocketMQMessageListener = new RocketMQMessageListener();
        rocketMQMessageListener.setMessageConverter(messageConverter);
        rocketMQMessageListener.setQueueHandlerMappingContainer(eventHandlerMappingContainer);

        RocketMQConsumer rocketMQConsumer = new RocketMQConsumer();
        rocketMQConsumer.setGroupName("consumer-group");
        rocketMQConsumer.setNameServerAddress("127.0.0.1:9876");
        rocketMQConsumer.setMessageListener(rocketMQMessageListener);

        TopicTagPair tagPair = new TopicTagPair();
        tagPair.setTopic("test-topic");
        tagPair.setTags(Collections.singletonList("*"));
        List<TopicTagPair> tagPairs = new ArrayList<TopicTagPair>();
        tagPairs.add(tagPair);
        rocketMQConsumer.setTopicConfigList(tagPairs);
        rocketMQConsumer.setThreadCount(8);
        rocketMQConsumer.start();

        while (true) {
            String msg = "msg" + System.currentTimeMillis();
            Thread.sleep(1000);
            publisher.publish(new HelloEvent(msg));
        }
    }
}
