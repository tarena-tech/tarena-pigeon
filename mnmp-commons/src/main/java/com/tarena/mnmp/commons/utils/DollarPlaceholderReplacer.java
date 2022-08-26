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

package com.tarena.mnmp.commons.utils;

import com.tarena.mnmp.commons.json.Json;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DollarPlaceholderReplacer {

    private Json jsonProvider;

    public void setJsonProvider(Json jsonProvider) {
        this.jsonProvider = jsonProvider;
    }

    public String buildContent(String templateContent, Map<String, Object> params) {
        if (templateContent == null) {
            return null;
        }
        if (params == null) {
            return templateContent;
        }
        Pattern p = Pattern.compile("(\\$\\{)([\\w]+)(\\})");
        Matcher m = p.matcher(templateContent);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String group = m.group(2);
            String val = (String) params.get(group);
            if (val != null) {
                m.appendReplacement(sb, val);
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
