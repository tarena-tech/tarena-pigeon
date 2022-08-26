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
import java.util.Date;
import lombok.Data;

@ApiModel(value = "供应商新增入参")
@Data
public class ProviderSaveParam {
    @ApiModelProperty(
        value = "主键",
        name = "id",
        required = false,
        example = "1"
    )
    private Long id;
    @ApiModelProperty(
        value = "供应商名称",
        name = "name",
        required = false,
        example = "ali"
    )
    private String name;
    @ApiModelProperty(
        value = "供应商编码",
        name = "code",
        required = false,
        example = "SMS_20220111"
    )
    private String code;
    @ApiModelProperty(
        value = "供应商访问key",
        name = "accessKey",
        required = true,
        example = "LTAI5tDUmvKicoxxx"
    )
    private String accessKey;
    @ApiModelProperty(
        value = "供应商访问Secret",
        name = "accessKeySecret",
        required = true,
        example = "I2rF9nFhmjlFXoOguxxx"
    )
    private String accessKeySecret;
    @ApiModelProperty(
        value = "业务类型",
        name = "noticeType",
        required = false,
        example = "1"
    )
    private Integer noticeType;
    @ApiModelProperty(
        value = "官方网站",
        name = "officialWebsite",
        required = false
    )
    private String officialWebsite;
    @ApiModelProperty(
        value = "联系人",
        name = "contacts",
        required = false,
        example = "张三"
    )
    private String contacts;

    @ApiModelProperty(value = "json配置项")
    private String clientConfig;
    @ApiModelProperty(
        value = "联系电话",
        name = "phone",
        required = false,
        example = "18800099000"
    )
    private String phone;
    @ApiModelProperty(
        value = "简介",
        name = "remarks",
        required = false,
        example = "ali"
    )
    private String remarks;
    @ApiModelProperty(
        value = "审核状态",
        name = "auditStatus",
        notes = "0审核中,1通过,-1未通过",
        required = false,
        example = "1"
    )
    private Integer auditStatus;
    @ApiModelProperty(
        value = "开启停用",
        name = "enabled",
        notes = "0停用,1开启",
        required = false,
        example = "1"
    )
    private Integer enabled;
    @ApiModelProperty(
        value = "创建时间",
        name = "createTime",
        required = false
    )
    private Date createTime;


    @ApiModelProperty(
        value = "更新时间",
        name = "updateTime",
        required = false
    )
    private Date updateTime;

}
