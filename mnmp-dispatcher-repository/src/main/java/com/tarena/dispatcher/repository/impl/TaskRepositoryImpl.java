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

package com.tarena.dispatcher.repository.impl;

import com.tarena.dispatcher.bo.SmsSignBO;
import com.tarena.dispatcher.bo.TaskBO;
import com.tarena.dispatcher.bo.TaskTargetBO;
import com.tarena.dispatcher.bo.TemplateBO;
import com.tarena.dispatcher.respository.TaskRepository;
import com.tarena.dispatcher.storage.mapper.SmsSignDao;
import com.tarena.dispatcher.storage.mapper.TaskDao;
import com.tarena.dispatcher.storage.mapper.TaskTargetDao;
import com.tarena.dispatcher.storage.mapper.TemplateDao;
import com.tarena.mnmp.domain.TaskDO;
import com.tarena.mnmp.domain.TaskTargetDO;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
     * 任务结束
     *
     * @param taskId
     * @param taskStatus
     * @return
     */
    @Override public Integer finishTask(Long taskId, Integer taskStatus) {
        return taskDao.finishTask(taskStatus, taskId);
    }

    @Override public Integer errorTask(Long taskId, Integer status, String error) {
        return taskDao.errorTask(status, error,taskId);
    }

    /**
     * 获取要执行的任务列表
     *
     * @return
     */
    @Override public List<TaskBO> queryTriggers() {
        List<TaskDO> resList = taskDao.queryTriggers();
        if (!CollectionUtils.isEmpty(resList)) {
            List<TaskBO> triggers = resList.stream().map(task -> {
                TaskBO bo = new TaskBO();
                BeanUtils.copyProperties(task, bo);
                return bo;
            }).collect(Collectors.toList());
            return triggers;
        }
        return null;
    }

    /**
     * 根据任务获取对应的目标信息
     *
     * @return
     */
    @Override public List<TaskTargetBO> getTargets(Long taskId) {
        List<TaskTargetDO> resList = taskTargetDao.queryListByTask(taskId);
        if (!CollectionUtils.isEmpty(resList)) {
            List<TaskTargetBO> targets = resList.stream().map(target -> {
                TaskTargetBO bo = new TaskTargetBO();
                BeanUtils.copyProperties(target, bo);
                return bo;
            }).collect(Collectors.toList());
            return targets;
        }
        return null;
    }

    @Override public TemplateBO queryTemplate(Long templateId) {
        TemplateBO template = new TemplateBO();
        BeanUtils.copyProperties(
            templateDao.selectById(templateId), template);

        return template;
    }

    @Override public SmsSignBO querySign(Long signId) {
        SmsSignBO sign = new SmsSignBO();
        BeanUtils.copyProperties(
            smsSignDao.selectById(signId), sign);
        return sign;
    }
}
