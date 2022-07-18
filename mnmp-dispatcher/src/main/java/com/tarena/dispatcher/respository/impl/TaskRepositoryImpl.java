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

package com.tarena.dispatcher.respository.impl;

import com.tarena.dispatcher.respository.TaskRepository;
import com.tarena.dispatcher.storage.entity.SmsSignDO;
import com.tarena.dispatcher.storage.entity.TaskDO;
import com.tarena.dispatcher.storage.entity.TaskTargetDO;
import com.tarena.dispatcher.storage.entity.TemplateDO;
import com.tarena.dispatcher.storage.mapper.SmsSignDao;
import com.tarena.dispatcher.storage.mapper.TaskDao;
import com.tarena.dispatcher.storage.mapper.TaskTargetDao;
import com.tarena.dispatcher.storage.mapper.TemplateDao;
import com.tarena.mnmp.enums.Deleted;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TaskTargetDao taskTargetDao;
    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private SmsSignDao smsSignDao;

    /**
     * 修改任务下次执行时间
     *
     * @param taskId
     * @param nextTriggerTime
     * @return
     */
    @Override public Integer updateNextTriggerTimeAndStatus(Long taskId, Date nextTriggerTime, Integer taskStatus) {
        return taskDao.updateNextTriggerTime(taskId, nextTriggerTime, taskStatus);
    }

    /**
     * 修改任务首次触发时间 在任务审核之后赋值即可
     *
     * @param taskId
     * @param triggerTime
     * @return
     */
    @Override public Integer updateStartTime(Long taskId, Date triggerTime) {
        return null;
    }

    /**
     * 任务结束
     *
     * @param taskId
     * @param taskStatus
     * @return
     */
    @Override public Integer finishTask(Long taskId, Integer taskStatus) {
        return taskDao.finishTask(taskStatus, taskId);
    }

    @Override public Integer updateTaskStatus(Long taskId, Integer taskStatus) {
        return taskDao.updateStatus(taskStatus, taskId);
    }

    /**
     * 获取要执行的任务列表
     *
     * @return
     */
    @Override public List<TaskDO> queryTriggers() {
        return taskDao.queryTriggers();
    }

    /**
     * 根据任务获取对应的目标信息
     *
     * @return
     */
    @Override public List<TaskTargetDO> getTargets(Long taskId) {
        TaskTargetDO taskTarget = new TaskTargetDO();
        taskTarget.setTaskId(taskId);
        taskTarget.setDeleted(Deleted.NO.getVal());
        return taskTargetDao.queryAll(taskTarget);
    }

    @Override public TemplateDO queryTemplate(Long templateId) {
        return templateDao.selectById(templateId);
    }

    @Override public SmsSignDO querySign(Long signId) {
        return smsSignDao.selectById(signId);
    }
}
