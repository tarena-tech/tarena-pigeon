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

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@Data
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
    private Integer templateId;

    /**
     * 签名ID
     */
    @ApiModelProperty(value = "签名ID 根据签名id可查看签名详情", required = true)
    private Integer signId;

    /**
     * 所属应用
     */
    @ApiModelProperty(value = "所属应用 根据id可查看应用详情", required = true)
    private Long appId;

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
    private Integer taskAudit;

    @ApiModelProperty(value = "审核时提交的文案", required = true)
    private String taskAuditResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getSignId() {
        return signId;
    }

    public void setSignId(Integer signId) {
        this.signId = signId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Integer getCycleLevel() {
        return cycleLevel;
    }

    public void setCycleLevel(Integer cycleLevel) {
        this.cycleLevel = cycleLevel;
    }

    public Integer getCycleNum() {
        return cycleNum;
    }

    public void setCycleNum(Integer cycleNum) {
        this.cycleNum = cycleNum;
    }

    public Date getFirstTriggerTime() {
        return firstTriggerTime;
    }

    public void setFirstTriggerTime(Date firstTriggerTime) {
        this.firstTriggerTime = firstTriggerTime;
    }

    public Date getTriggerEndTime() {
        return triggerEndTime;
    }

    public void setTriggerEndTime(Date triggerEndTime) {
        this.triggerEndTime = triggerEndTime;
    }

    public Date getNextTriggerTime() {
        return nextTriggerTime;
    }

    public void setNextTriggerTime(Date nextTriggerTime) {
        this.nextTriggerTime = nextTriggerTime;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    public String getTargetFileUrl() {
        return targetFileUrl;
    }

    public void setTargetFileUrl(String targetFileUrl) {
        this.targetFileUrl = targetFileUrl;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTaskAudit() {
        return taskAudit;
    }

    public void setTaskAudit(Integer taskAudit) {
        this.taskAudit = taskAudit;
    }

    public String getTaskAuditResult() {
        return taskAuditResult;
    }

    public void setTaskAuditResult(String taskAuditResult) {
        this.taskAuditResult = taskAuditResult;
    }
}
