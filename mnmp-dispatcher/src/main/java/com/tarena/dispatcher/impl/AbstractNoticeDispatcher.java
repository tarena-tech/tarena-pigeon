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

package com.tarena.dispatcher.impl;

import com.tarena.dispatcher.NoticeDispatcher;
import com.tarena.dispatcher.NoticeEventGetter;
import com.tarena.dispatcher.respository.TargetLogRepository;
import com.tarena.mnmp.commons.json.Json;
import com.tarena.mnmp.monitor.Monitor;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractNoticeDispatcher<T extends NoticeEventGetter> implements NoticeDispatcher<T>, InitializingBean {

    private DispatcherRegistry dispatcherRegistry = DispatcherRegistry.getInstance();

    protected Json jsonProvider;

    protected Monitor monitor;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    protected TargetLogRepository targetLogRepository;

    public void setJsonProvider(Json jsonProvider) {
        this.jsonProvider = jsonProvider;
    }

    public void setTargetLogRepository(TargetLogRepository targetLogRepository) {
        this.targetLogRepository = targetLogRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.dispatcherRegistry.put(this);
    }
}
