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
import java.util.GregorianCalendar;

public class TaskDO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务状态 0:未开启 1:推送中 2:终止 3:已结束 4.失败
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
     * 签名ID
     */
    private Long signId;

    /**
     * 所属应用
     */
    private Long appId;

    /**
     * 周期类型 1:小时 2:日 3:周 4:月 5:年
     */
    private Integer cycleLevel;

    /**
     * 周期数
     */
    private Integer cycleNum;

    /**
     * 任务首次触发时间
     */
    private Date firstTriggerTime;

    /**
     * 任务触发结束时间
     */
    private Date triggerEndTime;

    /**
     * 下次任务触发时间
     */
    private Date nextTriggerTime;

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
    private Integer creator;

    /**
     * 创建人邮箱前缀
     */
    private String creatorEmail;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 所属部门
     */
    private Long deptId;

    /**
     * 描述
     */
    private String remark;

    /**
     * 错误日志
     */
    private String error;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private Integer auditStatus;

    private String auditResult;

    private Integer mock;

    public Integer getMock() {
        return mock;
    }

    public void setMock(Integer mock) {
        this.mock = mock;
    }

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

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
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

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
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

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    @Override public String toString() {
        return "TaskDO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", taskStatus=" + taskStatus +
            ", taskType=" + taskType +
            ", noticeType=" + noticeType +
            ", templateId=" + templateId +
            ", signId=" + signId +
            ", appId=" + appId +
            ", cycleLevel=" + cycleLevel +
            ", cycleNum=" + cycleNum +
            ", firstTriggerTime=" + firstTriggerTime +
            ", triggerEndTime=" + triggerEndTime +
            ", nextTriggerTime=" + nextTriggerTime +
            ", targetType=" + targetType +
            ", targetFileName='" + targetFileName + '\'' +
            ", targetFileUrl='" + targetFileUrl + '\'' +
            ", creator=" + creator +
            ", creatorEmail='" + creatorEmail + '\'' +
            ", creatorName='" + creatorName + '\'' +
            ", deptId=" + deptId +
            ", remark='" + remark + '\'' +
            ", error='" + error + '\'' +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", auditStatus=" + auditStatus +
            ", auditResult='" + auditResult + '\'' +
            '}';
    }
}

