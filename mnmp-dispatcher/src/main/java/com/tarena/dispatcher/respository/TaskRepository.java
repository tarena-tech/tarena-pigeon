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

import com.tarena.dispatcher.bo.SmsSignBO;
import com.tarena.dispatcher.bo.TaskBO;
import com.tarena.dispatcher.bo.TaskTargetBO;
import com.tarena.dispatcher.bo.TemplateBO;
import java.util.Date;
import java.util.List;

public interface TaskRepository {

    Integer updateNextTriggerTimeAndStatus(Long taskId, Date nextTriggerTime, Integer taskStatus);

    Integer finishTask(Long taskId, Integer taskStatus);

    Integer errorTask(Long taskId,Integer status,String remark);

    List<TaskBO> queryTriggers();

    List<TaskTargetBO> getTargets(Long taskId);

    TemplateBO queryTemplate(Long templateId);

    SmsSignBO querySign(Long signId);

}
