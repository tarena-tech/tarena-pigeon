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

import com.tarena.mnmp.admin.codegen.api.task.TaskApi;
import com.tarena.mnmp.admin.view.task.TaskVO;
import com.tarena.mnmp.admin.view.task.TaskView;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.TaskDO;
import com.tarena.mnmp.domain.task.TaskPage;
import com.tarena.mnmp.domain.task.TaskQuery;
import com.tarena.mnmp.domain.task.TaskService;
import com.tarena.mnmp.domain.task.TaskStatistics;
import com.tarena.mnmp.enums.TaskStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController implements TaskApi {

    @Autowired
    private TaskService taskService;

    @Override public void addTask(TaskVO taskVO) {
        TaskDO bo = new TaskDO();
        BeanUtils.copyProperties(taskVO, bo);
        taskService.addTask(bo);
    }

    @Override public void doAudit(Long id, Integer auditStatus, String auditResult) {
        taskService.doAudit(id, auditStatus, auditResult);
    }

    @Override public PagerResult<TaskView> queryListByPage(TaskQuery taskQuery) {
        return null;
    }

    @Override public TaskDO queryTaskDetail(Long id) {
        return null;
    }

    @Override public TaskStatistics queryTaskStatistics(Long id) {
        return null;
    }

    @Override public void stopTask(Long id) {
        taskService.action(id, TaskStatus.TASK_STOP.status());
    }

    @Override public void updateTask(TaskVO taskVO) {
        TaskDO task = new TaskDO();
        BeanUtils.copyProperties(taskVO, task);
        taskService.updateTask(task);
    }
}
