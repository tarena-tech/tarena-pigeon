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
package com.tarena.mnmp.admin.controller.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@ApiModel(value = "task入参")
@Data
public class TaskParam {

    @ApiModelProperty(value = "主键id, 修改时必填", required = false)
    private Long id;

    @ApiModelProperty(value = "任务名称", required = true)
    @NotBlank(message = "任务名称为必填项")
    private String name;

    @ApiModelProperty(value = "任务类型 0:立即 1:定时 2:周期 3:条件规则触发", required = true)
    @NotNull(message = "任务类型为必填项")
    private Integer taskType;

    @ApiModelProperty(value = "通知类型： 1-sms,2-email,3-wechat", required = true)
    @NotNull(message = "通知类型为必填项")
    private Integer noticeType;

    @ApiModelProperty(value = "绑定的模板id，从模板列表拉取", required = true)
    @NotNull(message = "绑定的模板id为必填项")
    private Long templateId;

    @ApiModelProperty(value = "签名id，从签名列表拉取", required = true)
    @NotNull(message = "签名id为必填项")
    private Long signId;

    @ApiModelProperty(value = "appId 从下拉列表获取", required = true)
    @NotNull(message = "appId为必填项")
    private Long appId;

    @ApiModelProperty(value = "周期类型 0:分钟 1:小时 2:日 3:周 4:月 5:年", required = true)
    private Integer cycleLevel;

    @ApiModelProperty(value = "周期数", required = true)
    private Integer cycleNum;

    @ApiModelProperty(value = "创建人", required = true)
    private String creatorName;

    @ApiModelProperty(value = "结束时间", required = true)
    private Date triggerEndTime;

    @ApiModelProperty(value = "文件路径", required = true)
    @NotBlank(message = "请上传目标文件")
    private String filePath;

    @ApiModelProperty(value = "是否是mock接口， 0：否， 1：是", required = true)
    private Integer mock;

    @ApiModelProperty(value = "发送时间")
    private Date nextTriggerTime;


}
