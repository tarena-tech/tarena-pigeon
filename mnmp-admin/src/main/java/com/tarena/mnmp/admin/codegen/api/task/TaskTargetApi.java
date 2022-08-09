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

package com.tarena.mnmp.admin.codegen.api.task;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tarena.mnmp.admin.controller.task.TaskTargetView;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.task.TaskTargetParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Api(
    value = "TaskTarget",
    tags = "任务目标管理"
)
@RequestMapping("task/target")
public interface TaskTargetApi {

    @ApiOperationSupport(order = 6000)
    @ApiOperation(
        value = "查询任务列表信息（分页）"
    )
    @GetMapping(
        value = {"/query/page"}
    )
    PagerResult<TaskTargetView> queryListByPage(TaskTargetParam param);
}
