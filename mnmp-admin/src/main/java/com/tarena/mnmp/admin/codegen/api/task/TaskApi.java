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
import com.tarena.mnmp.admin.controller.task.TaskParam;
import com.tarena.mnmp.admin.controller.task.TaskView;
import com.tarena.mnmp.admin.param.AuditParam;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.task.TaskQuery;
import com.tarena.mnmp.domain.task.TaskStatistics;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Validated
@Api(
    value = "Task",
    tags = "任务管理"
)
@RequestMapping("/task")
public interface TaskApi {

    @ApiOperationSupport(order = 5000)
    @ApiOperation(
        value = "获取excel文件"
    )
    @GetMapping(
        value = {"excel"}
    )
    @ApiParam(value = "获取excel， 当path为空表示获取模板")
    void getExcel(String path, HttpServletResponse response) throws BusinessException;


    @ApiOperationSupport(order = 5001)
    @ApiOperation(
        value = "上传文件"
    )
    @PostMapping(
        value = {"uploadFile"}
    )
    Result<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException, BusinessException;

    @ApiOperationSupport(order = 5002)
    @ApiOperation(
        value = "新增任务",
        nickname = "addTask",
        notes = ""
    )
    @PostMapping(
        value = {"add"}
    )
    void addTask(@Valid @RequestBody TaskParam taskParam, HttpServletResponse response) throws IOException;

    @ApiOperationSupport(order = 5003)
    @ApiOperation(
        value = "任务操作审核",
        nickname = "doAudit",
        notes = ""
    )
    @PostMapping({"/audit"})
    void doAudit(@RequestBody AuditParam param) throws BusinessException;

    @ApiOperationSupport(order = 5004)
    @ApiOperation(
        value = "查询任务列表信息（分页）",
        nickname = "queryListByPage",
        notes = ""
    )
    @GetMapping(
        value = {"/query/page"}
    )
    PagerResult<TaskView> queryListByPage(TaskQuery taskQuery);

    @ApiOperationSupport(order = 5005)
    @ApiOperation(
        value = "查看任务详情",
        nickname = "queryTaskDetail",
        notes = ""
    )
    @GetMapping(
        value = {"detail"},
        produces = {"application/json"}
    )
    TaskView queryTaskDetail(@ApiParam(value = "任务id", required = true)Long id) throws BusinessException;

    @ApiOperationSupport(order = 5006)
    @ApiOperation(
        value = "查看任务发送统计",
        nickname = "queryTaskStatistics",
        notes = ""
    )
    @GetMapping(
        value = {"/taskStatistics"},
        produces = {"application/json"}
    )
    TaskStatistics queryTaskStatistics(
        @NotNull @ApiParam(value = "任务id", required = true) @Valid @RequestParam(value = "id", required = true) Long id);


    @ApiOperationSupport(order = 5009)
    @ApiOperation(
        value = "修改任务",
        nickname = "updateTask",
        notes = ""
    )
    @PutMapping({"modify"})
    void modify(@ApiParam(value = "更新任务", required = true) @Valid @RequestBody TaskParam taskParam);

    @PutMapping("change/task/status")
    void changeTaskStatus(Long id) throws BusinessException;
}
