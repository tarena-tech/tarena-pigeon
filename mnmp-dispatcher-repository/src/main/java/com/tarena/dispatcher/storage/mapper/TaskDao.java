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

package com.tarena.dispatcher.storage.mapper;

import com.tarena.dispatcher.storage.entity.TaskDO;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TaskDao {

    TaskDO queryById(Long id);

    void update(TaskDO task);

    List<TaskDO> queryTriggers();

    @Update("update notice_task set next_trigger_time = #{time}, task_status = #{taskStatus} , update_time = now() where id = #{id}")
    int updateNextTriggerTime(@Param("id") Long id, @Param("time") Date time, @Param("taskStatus") Integer taskStatus);

    @Update("update notice_task set task_status = #{taskStatus},next_trigger_time = null, update_time = now()" +
        " where id = #{id}")
    int finishTask(@Param("taskStatus") Integer taskStatus, @Param("id") Long id);

    @Update("update notice_task set task_status = #{taskStatus} , update_time = now()" +
        " where id = #{id}")
    int updateStatus(@Param("taskStatus") Integer taskStatus, @Param("id") Long id);

}
