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

package com.tarena.mnmp.monitor;

import com.tarena.mnmp.api.NoticeDTO;
import com.tarena.mnmp.commons.json.Json;
import com.tarena.mnmp.enums.TargetStatus;
import com.tarena.mnmp.protocol.NoticeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMonitor implements Monitor {
    private static Logger logger = LoggerFactory.getLogger(DefaultMonitor.class);

    private Json jsonProvider;

    public void setJsonProvider(Json jsonProvider) {
        this.jsonProvider = jsonProvider;
    }

    @Override public void alarms(String key, String msg) {
        logger.info("TARENA_MNMP_{}", key);
        logger.error("TARENA_MNMP_{},msg {}", key, msg);
    }

    @Override public void noticeRequest(NoticeDTO notice) {
        logger.info("TARENA_MNMP_NOTICE_REQUEST {}", this.jsonProvider.toString(notice));
    }

    @Override public void noticeStatus(NoticeEvent noticeEvent, String target, TargetStatus status) {
        logger.info("TARENA_MNMP_NOTICE [{}], TARGET {}, STATUS {}", noticeEvent.toString(), target, status);
    }

    @Override public void schedule() {
        logger.info("TARENA_MNMP_NOTICE SCHEDULE {}", System.currentTimeMillis());
    }
}
