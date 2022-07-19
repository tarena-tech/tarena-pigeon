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

package com.tarena.mnmp.task;

import java.util.Date;

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
     * 签名ID
     */
    private Long signId;
    /**
     * 所属应用
     */
    private Long appId;
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
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

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

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
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
}
