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

package com.tarena.mnmp.domain;

import java.util.Date;
import lombok.Data;

@Data
public class SmsTemplateDO {

    private Long id;

    private String code;

    private Long appId;

    private String appCode;

    private String name;

    private Integer templateType;

    private Integer noticeType;

    private String content;

    private String remark;

    private Integer auditStatus;

    private String auditResult;

    private Integer enabled;

    private Integer useCount;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    private Integer createUserId;

    private String createUserName;

    private Long providerId;

}
