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

package com.tarena.dispatcher.respository;

import com.tarena.dispatcher.storage.entity.SmsSignDO;
import com.tarena.dispatcher.storage.entity.TaskDO;
import com.tarena.dispatcher.storage.entity.TaskTargetDO;
import com.tarena.dispatcher.storage.entity.TemplateDO;
import java.util.Date;
import java.util.List;

public interface TaskRepository {

    Integer updateNextTriggerTimeAndStatus(Long taskId, Date nextTriggerTime,Integer taskStatus);

    Integer updateStartTime(Long taskId, Date triggerTime);

    Integer finishTask(Long taskId, Integer taskStatus);

    Integer updateTaskStatus(Long taskId, Integer taskStatus);

    List<TaskDO> queryTriggers();

    List<TaskTargetDO> getTargets(Long taskId);

    TemplateDO queryTemplate(Long templateId);

    SmsSignDO querySign(Long signId);

}
