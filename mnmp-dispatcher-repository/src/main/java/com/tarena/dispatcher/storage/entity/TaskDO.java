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

package com.tarena.dispatcher.storage.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class TaskDO implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务状态 0:未开启 1:推送中 2:终止 3:已结束
     */
    private Integer taskStatus;
    /**
     * 任务类型 0:立即 1:定时 2:周期 3:条件规则触发
     */
    private Integer taskType;
    /**
     * 消息类型
     */
    private Integer noticeType;
    /**
     * 消息模板主表ID
     */
    private Long templateId;
    /**
     * 消息类型子类型 1:sms-短信通知 2:sms-验证码 3:sms-推广短信
     */
    private Integer templateType;
    /**
     * 签名ID
     */
    private Long signId;
    /**
     * 所属应用
     */
    private Long appId;
    /**
     * 周期原始JSON串,用于编辑对比等;涉及多天则在触发器表对应多条
     */
    private String cycleStr;
    /**
     * 目标类型 1:文件上传 2.规则匹配
     */
    private Integer targetType;
    /**
     * 目标文件名称
     */
    private String targetFileName;
    /**
     * 目标文件地址
     */
    private String targetFileUrl;
    /**
     * 创建人
     */
    private Long creator;
    /**
     * 所属部门
     */
    private Long deptId;
    /**
     * 描述
     */
    private String remark;

    /**
     * 周期类型 1:分钟 2:日 3:周 4:月 5:年
     */
    private Integer cycleLevel;
    /**
     * 周期数
     */
    private Integer cycleNum;
    /**
     * 任务触发时间
     */
    private Date triggerTime;
    /**
     * 任务触发结束时间
     */
    private Date triggerEndTime;
    /**
     * 下次任务触发时间
     */
    private Date nextTriggerTime;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}