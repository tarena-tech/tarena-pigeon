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

package com.tarena.mnmp.admin.codegen.api.template;

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
}
