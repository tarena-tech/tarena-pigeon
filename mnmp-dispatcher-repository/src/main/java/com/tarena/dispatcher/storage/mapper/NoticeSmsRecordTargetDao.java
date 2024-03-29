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

import com.tarena.dispatcher.bo.PhoneBizIdReceiptBO;
import com.tarena.mnmp.domain.NoticeSmsRecordTargetDO;
import com.tarena.mnmp.enums.TargetStatus;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NoticeSmsRecordTargetDao {

    Integer insert(NoticeSmsRecordTargetDO data);

    void update(NoticeSmsRecordTargetDO data);

    void batchUpdate(List<PhoneBizIdReceiptBO> receiptedList);

    @Select("select status from notice_sms_record_target where task_id = #{taskId} " +
        "and trigger_time = #{triggerTime} and target = #{target}")
    Integer getStatusByTaskAndTriggerTime(@Param("taskId") Long taskId, @Param("triggerTime") String triggerTime,
        @Param("target") String target);

    /**
     * 获取已发送但未回执的目标数据
     *
     * @return
     * @see TargetStatus SENT_TO_PROVIDER
     */
    @Select("select id,task_id, target, content, status, trigger_time,push_time, biz_id, send_result," +
        " create_time, update_time from notice_sms_record_target where status=1 order by id limit 100")
    List<NoticeSmsRecordTargetDO> queryNotReceiptBizIds();
}
