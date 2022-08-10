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
package com.tarena.mnmp.domain.provider.controller;

import com.tarena.mnmp.api.TargetDTO;
import com.tarena.mnmp.enums.NoticeType;
import com.tarena.mnmp.enums.SendType;
import java.util.List;
import lombok.Data;

@Data
public class SendParam {


    public SendParam() {
        this.mock = 0;
    }

    private String triggerTime;
    private SendType sendType;
    private NoticeType noticeType;
    private String appCode;
    private String templateCode;
    private String templateContent;
    private List<TargetDTO> targets;
    private String signCode;
    private String signName;
    private String providerCode;
    private Integer mock;
    private Long taskId;
}
