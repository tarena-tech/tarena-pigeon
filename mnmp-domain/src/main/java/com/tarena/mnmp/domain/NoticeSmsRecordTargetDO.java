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

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class NoticeSmsRecordTargetDO implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 所属应用
     */
//    private Long appId;

    /**
     * 应用code
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
     * 消息发送状态
     */
    private Integer status;
    private Date triggerTime;
    /**
     * 发送时间
     */
    private Date pushTime;

    /**
     * 到达时间
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
