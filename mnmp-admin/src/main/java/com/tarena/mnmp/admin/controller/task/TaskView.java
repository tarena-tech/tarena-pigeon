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
package com.tarena.mnmp.admin.controller.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@Data
@ApiModel("任务数据模型")
public class TaskView {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    private Long id;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称", required = true)
    private String name;

    /**
     * 任务状态 0:未开启 1:推送中 2:终止 3:已结束 4.失败
     */
    @ApiModelProperty(value = "任务状态 0:未开启 1:推送中 2:终止 3:已结束 4.失败", required = true)
    private Integer taskStatus;

    /**
     * 任务类型 0:立即 1:定时 2:周期 3:条件规则触发
     */
    @ApiModelProperty(value = "任务类型 0:立即 1:定时 2:周期 3:条件规则触发", required = true)
    private Integer taskType;

    /**
     * 消息类型
     */
    @ApiModelProperty(value = "消息类型 1-sms 2-email 3-wechat", required = true)
    private Integer noticeType;

    /**
     * 消息模板主表ID
     */
    @ApiModelProperty(value = "消息模板主表ID 根据id可查看模板详情", required = true)
    private Long templateId;

    @ApiModelProperty(value = "模板名称", required = true)
    private String templateName;
    /**
     * 签名ID
     */
    @ApiModelProperty(value = "签名ID 根据签名id可查看签名详情", required = true)
    private Long signId;

    @ApiModelProperty(value = "签名名称", required = true)
    private String signName;

    /**
     * 所属应用
     */
    @ApiModelProperty(value = "所属应用 根据id可查看应用详情", required = true)
    private Long appId;

    @ApiModelProperty(value = "签名名称", required = true)
    private String appName;

    /**
     * 周期类型 1:小时 2:日 3:周 4:月 5:年
     */
    @ApiModelProperty(value = "周期类型 1:小时 2:日 3:周 4:月 5:年", required = true)
    private Integer cycleLevel;

    /**
     * 周期数
     */
    @ApiModelProperty(value = "周期数", required = true)
    private Integer cycleNum;

    /**
     * 任务首次触发时间
     */
    @ApiModelProperty(value = "任务首次触发时间", required = true)
    private Date firstTriggerTime;

    /**
     * 任务触发结束时间
     */
    @ApiModelProperty(value = "任务触发结束时间", required = true)
    private Date triggerEndTime;

    /**
     * 下次任务触发时间
     */
    @ApiModelProperty(value = "下次任务触发时间", required = true)
    private Date nextTriggerTime;

    /**
     * 目标文件名称
     */
    @ApiModelProperty(value = "目标文件名称", required = true)
    private String targetFileName;

    /**
     * 目标文件地址
     */
    @ApiModelProperty(value = "目标文件地址", required = true)
    private String targetFileUrl;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = true)
    private Integer creator;

//    /**
//     * 创建人邮箱前缀
//     */
//    @ApiModelProperty(value = "创建人邮箱前缀", required = true)
//    private String creatorEmail;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String creatorName;

//    /**
//     * 所属部门
//     */
//    @ApiModelProperty(value = "任务名称", required = true)
//    private Long deptId;

    /**
     * 描述
     */
    @ApiModelProperty(value = "remark", required = true)
    private String remark;

    /**
     * 错误日志
     */
    @ApiModelProperty(value = "错误日志, 字段可能很长", required = true)
    private String error;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", required = true)
    private Date updateTime;

    @ApiModelProperty(value = "审核状态 -1-审核拒绝， 0-待审核， 1-审核通过", required = true)
    private Integer auditStatus;

    @ApiModelProperty(value = "审核时提交的文案", required = true)
    private String auditResult;

    @ApiModelProperty(value = "是否是mock，0：否， 1：是", required = true)
    private Integer mock;

}
