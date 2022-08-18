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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tarena.mnmp.commons.pager.PagerResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
@ApiModel("任务发送记录查询模型")
public class SmsTargetRecordParam extends PagerResult {

    public SmsTargetRecordParam() {
        super();
        this.desc = true;
    }

    @ApiModelProperty("手机号，该字段为精确搜索")
    private String eqTarget;

    @ApiModelProperty("手机号，该字段为模糊搜索")
    private String target;

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("回执码搜索")
    private String bizId;

    @ApiModelProperty("应用编码")
    private String appCode;

    @ApiModelProperty("发送状态")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("任务执行时间 时间格式 yyyy-MM-dd HH:mm:ss ")
    private Date startTriggerTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("任务执行时间 时间格式 yyyy-MM-dd HH:mm:ss ")
    private Date endTriggerTime;

    @ApiModelProperty("true: 降序, false:正序")
    private Boolean desc;

    @JsonIgnore
    private Long createUserId;

    @JsonIgnore
    private List<String> appCodes;
}
