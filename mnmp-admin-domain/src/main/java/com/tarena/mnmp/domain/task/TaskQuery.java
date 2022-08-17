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


package com.tarena.mnmp.domain.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tarena.mnmp.commons.pager.PagerResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@ApiModel(value = "任务查询入参")
@Data
public class TaskQuery extends PagerResult {

    public TaskQuery() {
        super();
        this.desc = true;
    }

    @ApiModelProperty(value = "任务名 支持模糊")
    private String name;

    @ApiModelProperty(value = "appId")
    private Long appId;

    @ApiModelProperty(value = "任务状态 0:未开启 1:推送中 2:终止 3:已结束 4.失败")
    private Integer taskStatus;

    @ApiModelProperty(value = "审核状态 -1：拒绝， 0：待审核， 1：审核通过")
    private Integer auditStatus;

    @JsonIgnore
    private Long signId;

    @JsonIgnore
    private Long templateId;

    @JsonIgnore
    private List<Integer> taskStatusList;

    @JsonIgnore
    private Long createUserId;

    @ApiModelProperty(value = "是否倒叙 true:倒叙, false:正序")
    private Boolean desc;

}
