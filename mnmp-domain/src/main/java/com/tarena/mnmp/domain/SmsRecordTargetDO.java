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
package com.tarena.mnmp.domain;

import java.util.Date;
import lombok.Data;

@Data
public class SmsRecordTargetDO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 应用ID
     */
    private String appCode;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 目标
     */
    private String target;

    /**
     * 短信具体内容
     */
    private String content;

    /**
     * 消息发送状态 0发送给供应商失败 1发送给供应商成功 2发送给目标失败 3发送给目标成功
     */
    private Byte status;

    /**
     * 任务执行时间
     */
    private String triggerTime;

    /**
     * 发送时间
     */
    private Date pushTime;

    /**
     * 推送到达时间
     */
    private Date pushReceiveTime;

    /**
     * 发送回执码
     */
    private String bizId;

    /**
     * 发送结果/失败原因
     */
    private String sendResult;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}

