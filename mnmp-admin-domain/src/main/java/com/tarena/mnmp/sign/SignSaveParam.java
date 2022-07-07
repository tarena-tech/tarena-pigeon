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

package com.tarena.mnmp.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

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
    private String name;
    @ApiModelProperty(
        value = "应用名称",
        name = "appName",
        required = false
    )
    private String appName;
    @ApiModelProperty(
        value = "应用主键",
        name = "appId",
        required = false
    )
    private Long appId;
    @ApiModelProperty(
        value = "应用编码",
        name = "appCode",
        required = false
    )
    private String appCode;
    @ApiModelProperty(
        value = "简介",
        name = "remark",
        required = false,
        example = "测试签名简介"
    )
    private String remark;
    @ApiModelProperty(
        value = "创建者",
        name = "creator",
        required = false
    )
    private String creator;
    @ApiModelProperty(
        value = "状态",
        name = "status",
        required = false
    )
    private Integer status;
    @ApiModelProperty(
        value = "创建时间",
        name = "createTime",
        required = false
    )
    private Date createTime;

    @ApiModelProperty(
        value = "修改时间",
        name = "createTime",
        required = false
    )
    private Date updateTime;



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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
