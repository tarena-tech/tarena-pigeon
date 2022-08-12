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
public class AppDO {

    private Long id;

    private String code;

    private String name;

    private String leader;

    private String teamMembers;

    private String remarks;

    private Integer enabled;

    private Integer auditStatus;

    private String auditResult;

    private Date createTime;

    private Date updateTime;

    public void cleanSameData () {
        this.createTime = null;
        this.auditResult = null;
        this.auditStatus = null;
        this.enabled = null;
    }
}
