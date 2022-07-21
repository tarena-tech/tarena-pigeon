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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private static final int AUDIT_STATUS_PASS = 1;

    @Autowired
    private TaskDao taskDao;

    public void doAudit(Long id, Integer status, String result) {
        TaskDO taskDO = taskDao.queryById(id);
        //todo 审核逻辑处理 修改任务状态,备注说明等

        //审核通过后初始化执行时间
        if (AUDIT_STATUS_PASS == status.intValue()) {
            taskDO.setNextTriggerTime(taskDO.getFirstTriggerTime());
        }
        taskDao.update(taskDO);
    }
}
