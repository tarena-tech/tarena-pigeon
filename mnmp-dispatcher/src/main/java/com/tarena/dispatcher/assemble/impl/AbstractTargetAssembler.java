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

package com.tarena.dispatcher.assemble.impl;

import com.tarena.dispatcher.NoticeEventGetter;
import com.tarena.dispatcher.assemble.TargetAssembler;
import com.tarena.mnmp.commons.utils.DollarPlaceholderReplacer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractTargetAssembler<T extends NoticeEventGetter> implements TargetAssembler<T>, InitializingBean {
    private TargetAssemblerRegistry targetAssemblerRegistry = TargetAssemblerRegistry.getInstance();

    protected DollarPlaceholderReplacer dollarPlaceholderReplacer;

    protected Map<String, Function<String, String>> contentConvert = new HashMap<>();

    public void setDollarPlaceholderReplacer(DollarPlaceholderReplacer dollarPlaceholderReplacer) {
        this.dollarPlaceholderReplacer = dollarPlaceholderReplacer;
    }

    public void contentConvertPut(String key, Function<String, String> func) {
        contentConvert.put(key, func);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.targetAssemblerRegistry.put(this);
    }
}
