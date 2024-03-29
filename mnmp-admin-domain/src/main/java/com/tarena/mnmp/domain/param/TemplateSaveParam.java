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

package com.tarena.mnmp.domain.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "模板入参")
public class TemplateSaveParam {
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
        value = "通知类型",
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
        value = "是否可用",
        name = "enabled")
    private Integer enabled;
}
