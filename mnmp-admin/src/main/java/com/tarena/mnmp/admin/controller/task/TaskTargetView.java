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

import com.tarena.mnmp.domain.TaskTargetDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
@ApiModel("任务目标数据模型")
public class TaskTargetView {

    private Long id;

    @ApiModelProperty("消息任务ID")
    private Long taskId;

    @ApiModelProperty("目标电话")
    private String target;

    @ApiModelProperty("参数")
    private String params;

    @ApiModelProperty("是否删除 0否 1是")
    private Integer deleted;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public static List<TaskTargetView> convert(List<TaskTargetDO> sources) {
        if (null == sources) {
            return new ArrayList<>();
        }
        List<TaskTargetView> list = new ArrayList<>(sources.size());

        for (TaskTargetDO source : sources) {
            TaskTargetView tv = new TaskTargetView();
            BeanUtils.copyProperties(source, tv);
            list.add(tv);
        }
        return list;
    }
}
