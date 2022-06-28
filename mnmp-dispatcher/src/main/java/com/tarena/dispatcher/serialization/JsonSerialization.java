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

package com.tarena.dispatcher.serialization;

import com.alibaba.fastjson2.JSON;
import com.tarena.mnmp.api.NoticeTargetEvent;
import java.util.List;

public class JsonSerialization implements Serialization {
    @Override public <T extends NoticeTargetEvent> String serialize(List<T> targets) {
        return JSON.toJSONString(targets);
    }

    @Override public <T extends NoticeTargetEvent> List<T> deserialize(String targets, Class clazz) {
        return JSON.parseArray(targets, clazz);
    }
}
