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

import com.tarena.mnmp.commons.json.Json;
import com.tarena.mnmp.commons.mq.MQClient;
import com.tarena.mnmp.commons.mq.MQEvent;
import java.io.UnsupportedEncodingException;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonMessageConverter implements MessageConverter {

    private static Logger logger = LoggerFactory.getLogger(JsonMessageConverter.class);

    private String charset;

    private Json jsonProvider;

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setJsonProvider(Json jsonProvider) {
        this.jsonProvider = jsonProvider;
    }

    @Override
    public MQEvent fromMessage(Message message) {
        String json = null;
        try {
            json = new String(message.getBody(), charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("unsupported encodingException {}", charset);
            throw new RuntimeException(e);
        }

        String className = message.getProperties().get(MQClient.CLASS_NAME);
        try {
            return (MQEvent) jsonProvider.parse(json, Class.forName(className));
        } catch (ClassNotFoundException e) {
            logger.error("class{} not found", className);
            return null;
        }
    }

    @Override
    public Message createMessage(String topic, String tag, MQEvent event) {
        String jsonString = jsonProvider.toString(event);
        try {
            byte[] bytes = jsonString.getBytes(charset);
            Message message = new Message(topic, tag, bytes);
            message.getProperties().put(MQClient.CLASS_NAME, event.getClass().getName());
            return message;
        } catch (UnsupportedEncodingException e) {
            logger.error("unsupported encodingException {}", charset);
            throw new RuntimeException(e);
        }
    }
}