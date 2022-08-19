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

package com.tarena.mnmp.domain.provider.config;

import com.alibaba.csp.sentinel.datasource.FileRefreshableDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.io.File;
import java.util.List;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;


public class SentinelDataSourceInitFunc implements InitFunc {

    @Override public void init() throws Exception {

        String property = SentinelConfiguration.applicationContext.getEnvironment().getProperty("alibaba.sentinel.config.path");
        Resource resource = new PathResource(property);
        File[] files = resource.getFile().listFiles();
        for (File file : files) {
            if ("FlowRule.json".equals(file.getName())) {
                FileRefreshableDataSource<List<FlowRule>> sourceData = new FileRefreshableDataSource<>(
                    file, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() { }));

                FlowRuleManager.register2Property(sourceData.getProperty());
            }
            if ("ParamFlowRule.json".equals(file.getName())) {
                FileRefreshableDataSource<List<ParamFlowRule>> sourceData = new FileRefreshableDataSource<>(
                    file, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() { }));
                ParamFlowRuleManager.register2Property(sourceData.getProperty());
            }
        }

    }
}
