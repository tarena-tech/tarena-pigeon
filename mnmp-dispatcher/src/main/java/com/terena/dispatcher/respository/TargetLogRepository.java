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

package com.terena.dispatcher.respository;

import com.tarena.dispatcher.EmailNoticeTarget;
import com.tarena.dispatcher.SmsNoticeTarget;
import com.tarena.mnmp.commons.enums.TargetStatus;

public interface TargetLogRepository {

    TargetStatus getSmsStatus(Integer taskId, String target);

    TargetStatus getEmailStatus(Integer taskId, String target);

    TargetStatus getWechatStatus(Integer taskId, String target);

    Integer modifySmsStatus(Integer taskId, String target, TargetStatus targetStatus);

    Integer modifyEmailStatus(Integer taskId, String target, TargetStatus targetStatus);

    Integer modifyWechatStatus(Integer taskId, String target, TargetStatus targetStatus);

    void saveSmsTargetLog(Integer taskId, SmsNoticeTarget smsNoticeTarget);

    void saveEmailTargetLog(Integer taskId, EmailNoticeTarget emailNoticeTarget);
}
