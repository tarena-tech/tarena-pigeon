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
package com.tarena.mnmp.app;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotNull;

@ApiModel(value = "应用新增入参")
public class AppSaveParam {

    @ApiModelProperty(value = "应用编码", name = "code", required = true)
    @NotNull(message = "请填写应用编码")
    private String code;
    @ApiModelProperty(value = "应用名称", name = "name", required = true)
    @NotNull(message = "请填写应用名称")
    private String name;
    @ApiModelProperty(value = "应用负责人", name = "leader", required = true)
    @NotNull(message = "请填写应用负责人")
    private String leader;
    @ApiModelProperty(value = "应用组员", name = "teamMembers", required = false)
    private String teamMembers;
    @ApiModelProperty(value = "应用简介", name = "remarks", required = true)
    private String remarks;
    @ApiModelProperty(value = "应用状态", name = "status", required = false)
    private Integer status;
    @ApiModelProperty(value = "应用创建时间", name = "createTime", required = false)
    private Date createTime;
    @ApiModelProperty(value = "应用更新时间", name = "updateTime", required = false)
    private Date updateTime;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
