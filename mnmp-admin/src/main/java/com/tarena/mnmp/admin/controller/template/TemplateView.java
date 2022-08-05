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

package com.tarena.mnmp.admin.controller.template;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value = "短信模板控制层出参")
public class TemplateView {
    @ApiModelProperty(
        value = "主键",
        name = "id")
    private Long id;

    @ApiModelProperty(
        value = "模板編碼",
        name = "code")
    private String code;

    @ApiModelProperty(
        value = "应用ID",
        name = "appId")
    private Long appId;

    @ApiModelProperty(
        value = "应用编码",
        name = "appCode")
    private String appCode;

    @ApiModelProperty(
        value = "模板名称",
        name = "name")
    private String name;

    @ApiModelProperty(
        value = "模板模型",
        name = "templateType")
    private Integer templateType;


    @ApiModelProperty(
        value = "通知類型",
        name = "noticeType")
    private Integer noticeType;

    @ApiModelProperty(
        value = "模板内容",
        name = "content")
    private String content;

    @ApiModelProperty(
        value = "备注",
        name = "remark")
    private String remark;

    @ApiModelProperty(
        value = "审核状态",
        name = "auditStatus")
    private Integer auditStatus;

    @ApiModelProperty(
        value = "审核结果",
        name = "auditResult")
    private String auditResult;

    @ApiModelProperty(
        value = "是否可用",
        name = "enabled")
    private Integer enabled;

    @ApiModelProperty(
        value = "引用数",
        name = "useCount")
    private Integer useCount;

    @ApiModelProperty(
        value = "更新时间",
        name = "updateTime")
    private Date updateTime;

    private Date createTime;

    @ApiModelProperty(
        value = "是否删除 0:未删除，1：已删除",
        name = "deleted")
    private Integer deleted;

    @ApiModelProperty(
        value = "创建人 id",
        name = "creator")
    private Integer createUserId;

    @ApiModelProperty(
        value = "创建用户名",
        name = "createUserName")
    private String createUserName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
