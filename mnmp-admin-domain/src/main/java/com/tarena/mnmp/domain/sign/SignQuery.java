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

package com.tarena.mnmp.domain.sign;

import com.tarena.mnmp.commons.pager.PagerResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "签名查询入参")
public class SignQuery extends PagerResult {

    public SignQuery() {
        super();
        this.orderBy = true;
    }

    @ApiModelProperty(
        value = "应用编码",
        name = "appCode"
    )
    private String appCode;
    @ApiModelProperty(
        value = "审核状态",
        name = "auditStatus"
    )
    private Integer auditStatus;
    @ApiModelProperty(
        value = "签名名称",
        name = "name"
    )
    private String name;

    @ApiModelProperty("0:禁用， 1：启用")
    private Integer enable;

    @ApiModelProperty("true:倒叙， false：正序")
    private Boolean orderBy;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Boolean getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Boolean orderBy) {
        this.orderBy = orderBy;
    }
}
