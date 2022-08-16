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

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@ApiModel(value = "应用新增入参")
@Data
public class AppSaveParam {
    @ApiModelProperty(value = "主键 修改时必填", name = "id", required = false, example = "1")
    private Long id;

    @ApiModelProperty(value = "应用编码", name = "code", required = true, example = "APP_001")
    @NotBlank(message = "请填写应用编码")
    private String code;

    @ApiModelProperty(value = "应用名称", name = "name", required = true, example = "酷鲨商城用户中台")
    @NotNull(message = "请填写应用名称")
    private String name;

    @ApiModelProperty(value = "应用负责人", name = "leader", required = true, example = "charlie")
    @NotNull(message = "请填写应用负责人")
    private String leader;

    @ApiModelProperty(value = "应用组员", name = "teamMembers", notes = "使用,号隔开不同成员", example = "张三,李四,王五")
    private String teamMembers;

    @ApiModelProperty(value = "应用简介", name = "remarks", required = true, example = "java教学电商项目")
    @NotNull(message = "请填写应用简介")
    private String remarks;

    @ApiModelProperty(value = "应用开启停用", name = "enabled", required = false)
    private Integer enabled;

    @JsonIgnore
    private Long createUserId;
}
