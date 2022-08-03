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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "签名新增入参")
public class SignSaveParam {
    @ApiModelProperty(
        value = "主键",
        name = "id",
        required = false
    )
    private Long id;

    @ApiModelProperty(
        value = "签名名称",
        name = "name",
        required = false
    )
    @NotBlank(message = "签名名称不能为空")
    private String name;
    @ApiModelProperty(
        value = "应用主键",
        name = "appId",
        required = false
    )
    @NotNull(message = "请选择应用")
    private Long appId;
    @ApiModelProperty(
        value = "应用编码",
        name = "appCode",
        required = false
    )
    @NotBlank(message = "请选择应用")
    private String appCode;
    @ApiModelProperty(
        value = "简介",
        name = "remark",
        required = false,
        example = "测试签名简介"
    )
    private String remarks;

    private Integer enabled;


    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
