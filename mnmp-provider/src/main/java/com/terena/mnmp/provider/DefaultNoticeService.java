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

package com.terena.mnmp.provider;

import com.terena.dispatcher.assemble.impl.TargetAssemblerRegistry;
import com.terena.dispatcher.impl.DispatcherRegistry;
import com.terena.mnmp.api.NoticeDTO;
import com.terena.mnmp.api.NoticeService;
import com.terena.mnmp.api.NoticeTargetEvent;
import com.terena.mnmp.commons.protocol.BusinessException;
import java.util.List;

public class DefaultNoticeService implements NoticeService {

    @Override
    public <T extends NoticeTargetEvent> void send(NoticeDTO notice) throws BusinessException {
        List<T> targets = TargetAssemblerRegistry.getInstance().assemble(notice);
        DispatcherRegistry.getInstance().dispatcher(targets);
    }
}
