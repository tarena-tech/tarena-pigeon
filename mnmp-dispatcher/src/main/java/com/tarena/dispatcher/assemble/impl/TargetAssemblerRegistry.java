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
import com.tarena.mnmp.api.NoticeDTO;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TargetAssemblerRegistry {
    private static Logger logger = LoggerFactory.getLogger(TargetAssemblerRegistry.class);

    private TargetAssemblerRegistry() {
    }

    static class Nested {
        private static TargetAssemblerRegistry targetAssemblerRegistry = new TargetAssemblerRegistry();
    }

    public static TargetAssemblerRegistry getInstance() {
        return Nested.targetAssemblerRegistry;
    }

    private Map<String, TargetAssembler> targetAssemblerContainer = new HashMap<>();

    public void put(TargetAssembler targetAssembler) {
        String noticeType = targetAssembler.getNoticeType();
        this.targetAssemblerContainer.put(noticeType, targetAssembler);
    }

    public <T extends NoticeEventGetter> T assemble(NoticeDTO notice) {
        return this.assemble(notice, 0);
    }

    public <T extends NoticeEventGetter> T assemble(NoticeDTO notice, Integer batchIndex) {
        String noticeTypeName = notice.getNoticeType().name();
        TargetAssembler targetAssembler = targetAssemblerContainer.get(noticeTypeName);
        if (targetAssembler == null) {
            logger.error("target assembler [{}] not found", noticeTypeName);
            return null;
        }
        return (T) targetAssembler.assemble(notice, batchIndex);
    }
}
