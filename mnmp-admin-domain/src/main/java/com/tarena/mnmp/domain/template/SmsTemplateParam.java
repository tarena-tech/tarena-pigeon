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
package com.tarena.mnmp.domain.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@ApiModel(value = "创建模板入参")
@Data
public class SmsTemplateParam {

    @Min(value = 1, message = "非法数据")
    private Long id;

    @ApiModelProperty(value = "模板编码", required = true)
    @NotBlank(message = "模板编码不能为空")
    private String code;

    @ApiModelProperty(value = "appId", required = true)
    @NotNull(message = "appid为必填项")
    private Long appId;

    @ApiModelProperty(value = "appCode", required = true)
    private String appCode;

    @ApiModelProperty(value = "模板名称", required = true)
    @NotBlank(message = "模板名称为必填项")
    private String name;

    @ApiModelProperty(value = "模板类型: 0-全部，1-短信通通知，2-验证码，3-推广短信", required = true)
    @NotNull(message = "模板类型必填项")
    private Integer templateType;

    @ApiModelProperty("通知类型： 1-sms,2-email,3-wechat")
    @NotNull(message = "通知类型为必填项")
    private Integer noticeType;

    @ApiModelProperty(value = "模板内容",required = true)
    @NotBlank(message = "模板内容为必填项")
    private String content;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("审核状态, 修改时 使用")
    private Integer auditStatus;

    @ApiModelProperty("审核时填写的文案, 修改时 使用")
    private String auditResult;

    @ApiModelProperty("供应商id")
    private Long providerId;

    @ApiModelProperty("是否可用 (0否 1是）, 修改时 使用")
    private Integer enabled;

    @JsonIgnore
    private Long createUserId;


}
