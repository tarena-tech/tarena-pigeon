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

package com.tarena.mnmp.provider;

import com.tarena.dispatcher.NoticeEventGetter;
import com.tarena.dispatcher.assemble.impl.TargetAssemblerRegistry;
import com.tarena.dispatcher.impl.DispatcherRegistry;
import com.tarena.mnmp.api.NoticeDTO;
import com.tarena.mnmp.api.NoticeService;
import com.tarena.mnmp.monitor.Monitor;
import com.tarena.mnmp.protocol.BusinessException;

public class DefaultNoticeService implements NoticeService {
    private Monitor monitor;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override public void send(NoticeDTO notice) throws BusinessException {
        this.monitor.noticeRequest(notice);
        NoticeEventGetter noticeEvent = TargetAssemblerRegistry.getInstance().assemble(notice);
        DispatcherRegistry.getInstance().dispatcher(noticeEvent);
    }
}
