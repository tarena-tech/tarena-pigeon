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

package com.tarena.mnmp.admin.controller.app;

import com.github.xiaoymin.knife4j.annotations.Ignore;
import com.tarena.mnmp.domain.AppDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;

@ApiModel(value = "应用控制层出参")
public class AppView {
    @ApiModelProperty(
        value = "主键",
        name = "id")
    private Long id;
    @ApiModelProperty(
        value = "应用编码",
        name = "code")
    private String code;
    @ApiModelProperty(
        value = "应用名称",
        name = "name")
    private String name;
    @ApiModelProperty(
        value = "应用负责人",
        name = "leader")
    private String leader;
    @ApiModelProperty(
        value = "应用组员",
        name = "teamMembers")
    private String teamMembers;
    @ApiModelProperty(
        value = "应用简介",
        name = "remarks")
    private String remarks;
    @ApiModelProperty(
        value = "应用审核状态",
        name = "auditStatus"
    )
    private Integer auditStatus;
    @ApiModelProperty(
        value = "应用开启停用",
        name = "enabled"
    )
    private Integer enabled;
    @ApiModelProperty(
        value = "应用创建时间",
        name = "createTime")
    private Date createTime;
    @ApiModelProperty(
        value = "应用更新时间",
        name = "updateTime")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
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

    @Ignore
    public static List<AppView> convert(List<AppDO> source) {
        List<AppView> list = new ArrayList<>();
        for (AppDO appDO : source) {
            AppView appVO = new AppView();
            BeanUtils.copyProperties(appDO, appVO);
            list.add(appVO);
        }
        return list;
    }

}
