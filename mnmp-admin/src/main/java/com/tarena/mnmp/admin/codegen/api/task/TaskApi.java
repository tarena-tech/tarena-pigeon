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
import com.tarena.mnmp.task.Task;
import com.tarena.mnmp.task.TaskPage;
import com.tarena.mnmp.task.TaskQuery;
import com.tarena.mnmp.task.TaskStatistics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @ApiOperationSupport(order = 5001)
    @ApiOperation(
        value = "新增任务",
        nickname = "addTask",
        notes = ""
    )
    @PostMapping({"/api/task"})
    void addTask(@NotNull @ApiParam(value = "任务名称",required = true) @Valid @RequestParam(value = "name",required = true) String name, @NotNull @ApiParam(value = "应用Id",required = true) @Valid @RequestParam(value = "appId",required = true) Long appId, @NotNull @ApiParam(value = "消息类型 1:SMS 3:WECOM",required = true) @Valid @RequestParam(value = "noticeType",required = true) Integer noticeType, @NotNull @ApiParam(value = "消息子类型 1:文本 2:图片 3:视频 4:文件 5:文本卡片",required = true) @Valid @RequestParam(value = "templateType",required = true) Integer templateType, @NotNull @ApiParam(value = "模板ID",required = true) @Valid @RequestParam(value = "templateId",required = true) Long templateId, @NotNull @ApiParam(value = "任务类型",required = true) @Valid @RequestParam(value = "taskType",required = true) Integer taskType, @NotNull @ApiParam(value = "备注",required = true) @Valid @RequestParam(value = "remark",required = true) String remark, @NotNull @ApiParam(value = "上传文件",required = true) @Valid @RequestParam(value = "file",required = true) MultipartFile file, @NotNull @ApiParam(value = "审核状态 -1草稿 0审核中",required = true) @Valid @RequestParam(value = "auditStatus",required = true) Integer auditStatus, @ApiParam("签名ID") @Valid @RequestParam(value = "signId",required = false) Long signId, @ApiParam("发送时间") @Valid @RequestParam(value = "triggerTime",required = false) Long triggerTime, @ApiParam("发送结束时间") @Valid @RequestParam(value = "triggerEndTime",required = false) Long triggerEndTime, @ApiParam("周期级别") @Valid @RequestParam(value = "cycleLevel",required = false) Integer cycleLevel, @ApiParam("周期数") @Valid @RequestParam(value = "cycleNum",required = false) Integer cycleNum);

    @ApiOperationSupport(order = 5002)
    @ApiOperation(
        value = "任务操作审核",
        nickname = "doAudit",
        notes = ""
    )
    @PutMapping({"/api/task/{id}/audit/{auditStatus}"})
    void doAudit(
        @ApiParam(value = "要审核的任务id",required = true) @PathVariable("id") Long id,
        @ApiParam(value = "审核状态 1通过 2未通过",required = true) @PathVariable("auditStatus") Integer auditStatus,
        @ApiParam("审核意见") @Valid @RequestParam(value = "auditResult",required = false) String auditResult);

    @ApiOperationSupport(order = 5003)
    @ApiOperation(
        value = "查询任务列表信息（分页）",
        nickname = "queryListByPage",
        notes = "",
        response = TaskPage.class
    )
    @PostMapping(
        value = {"/api/task/page"},
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    TaskPage queryListByPage(@ApiParam(value = "任务查询参数",required = true) @Valid @RequestBody TaskQuery taskQuery);

    @ApiOperationSupport(order = 5004)
    @ApiOperation(
        value = "查看任务详情",
        nickname = "queryTaskDetail",
        notes = "",
        response = Task.class
    )
    @GetMapping(
        value = {"/api/task/{id}"},
        produces = {"application/json"}
    )
    Task queryTaskDetail(@ApiParam(value = "任务id",required = true) @PathVariable("id") Long id);

    @ApiOperationSupport(order = 5005)
    @ApiOperation(
        value = "查看任务发送统计",
        nickname = "queryTaskStatistics",
        notes = "",
        response = TaskStatistics.class
    )
    @GetMapping(
        value = {"/api/task/taskStatistics"},
        produces = {"application/json"}
    )
    TaskStatistics queryTaskStatistics(@NotNull @ApiParam(value = "任务id",required = true) @Valid @RequestParam(value = "id",required = true) Long id);
    @ApiOperationSupport(order = 5006)
    @ApiOperation(
        value = "终止任务",
        nickname = "stopTask",
        notes = ""
    )
    @PutMapping({"/api/task/stop/{id}"})
    void stopTask(@ApiParam(value = "任务id",required = true) @PathVariable("id") Long id);

    @ApiOperationSupport(order = 5007)
    @ApiOperation(
        value = "修改任务",
        nickname = "updateTask",
        notes = ""
    )
    @PutMapping({"/api/task"})
    void updateTask(
        @NotNull @ApiParam(value = "任务Id",required = true) @Valid @RequestParam(value = "id",required = true) Long id,
        @NotNull @ApiParam(value = "任务名称",required = true) @Valid @RequestParam(value = "name",required = true) String name,
        @NotNull @ApiParam(value = "应用Id",required = true) @Valid @RequestParam(value = "appId",required = true) Long appId,
        @NotNull @ApiParam(value = "消息类型",required = true) @Valid @RequestParam(value = "noticeType",required = true) Integer noticeType,
        @NotNull @ApiParam(value = "消息子类型",required = true) @Valid @RequestParam(value = "templateType",required = true) Integer templateType,
        @NotNull @ApiParam(value = "模板ID",required = true) @Valid @RequestParam(value = "templateId",required = true) Long templateId,
        @NotNull @ApiParam(value = "任务类型",required = true) @Valid @RequestParam(value = "taskType",required = true) Integer taskType,
        @NotNull @ApiParam(value = "备注",required = true) @Valid @RequestParam(value = "remark",required = true) String remark,
        @NotNull @ApiParam(value = "审核状态 -1草稿 0审核中",required = true) @Valid @RequestParam(value = "auditStatus",required = true) Integer auditStatus,
        @ApiParam("签名ID") @Valid @RequestParam(value = "signId",required = false) Long signId, @ApiParam("发送时间") @Valid @RequestParam(value = "triggerTime",required = false) Long triggerTime,
        @ApiParam("发送结束时间") @Valid @RequestParam(value = "triggerEndTime",required = false) Long triggerEndTime,
        @ApiParam("周期级别") @Valid @RequestParam(value = "cycleLevel",required = false) Integer cycleLevel,
        @ApiParam("周期数") @Valid @RequestParam(value = "cycleNum",required = false) Integer cycleNum,
        @ApiParam("上传文件") @Valid @RequestParam(value = "file",required = false) MultipartFile file);

}
