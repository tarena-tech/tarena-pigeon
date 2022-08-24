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

package com.tarena.mnmp.commons;

import com.tarena.mnmp.commons.json.Json;
import com.tarena.mnmp.commons.utils.DollarPlaceholderReplacer;
import com.tarena.mnmp.extentions.autoconfigurations.FastJson;
import java.util.HashMap;
import java.util.Map;

public class DollarPlaceholderReplacerTest {
    public static void main(String[] args) {
        DollarPlaceholderReplacer dollarPlaceholderReplacer = new DollarPlaceholderReplacer();
        Json json = new FastJson();
        dollarPlaceholderReplacer.setJsonProvider(json);
        Map<String, Object> param = new HashMap<>();
        param.put("name", "zhangsan");
        param.put("time", "2021-11-11");
        param.put("courseName", "语文");
        System.out.println("template-parameters "+json.toString(param));

        String content = dollarPlaceholderReplacer.buildContent("{\"content\":\"hello:${name},您于${time}有${courseName}课\"}", param);
        System.out.println(content);
    }
}
