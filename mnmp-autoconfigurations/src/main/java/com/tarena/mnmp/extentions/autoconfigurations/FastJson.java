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

import com.alibaba.fastjson2.JSON;
import com.tarena.mnmp.commons.json.Json;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FastJson implements Json {
    @Override public String toString(Serializable model) {
        return JSON.toJSONString(model);
    }

    @Override
    public String toString(Map<String, Object> map) {
        return JSON.toJSONString(map);
    }

    @Override
    public <T> String toString(Collection<T> models) {
        return JSON.toJSONString(models);
    }

    @Override
    public <T> T parse(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    @Override
    public <T> List<T> parseList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    @Override
    public Map<String, Object> parse(String json) {
        return JSON.parseObject(json,Map.class);
    }
}
