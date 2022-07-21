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

package com.tarena.schedule.utils;

import com.tarena.mnmp.enums.CycleLevel;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class NoticeTaskTrigger implements Serializable {
    private static final long serialVersionUID = 514888347075282419L;
    /**
     * 任务 id
     */
    private Long taskId;
    /**
     * 消息类型
     */
    private Integer noticeType;
    /**
     * 任务 发送类型
     */
    private Integer taskType;
    /**
     * 周期类型
     */
    private Integer cycleLevel;
    /**
     * 周期数
     */
    private Integer cycleNum;
    /**
     * 任务开始时间
     */
    private Date firstTriggerTime;
    /**
     * 任务结束时间
     */
    private Date triggerEndTime;
    /**
     * 下次任务触发时间
     */
    private Date nextTriggerTime;

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

    public Date getNextTriggerTime() {
        return nextTriggerTime;
    }

    public void setNextTriggerTime(Date nextTriggerTime) {
        this.nextTriggerTime = nextTriggerTime;
    }

    public boolean isFinish(Date nextTriggerTime) {
        //下次执行时间大于任务结束时间则任务结束
        return nextTriggerTime == null || nextTriggerTime.after(this.triggerEndTime);
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Date getFirstTriggerTime() {
        return firstTriggerTime;
    }

    public void setFirstTriggerTime(Date firstTriggerTime) {
        this.firstTriggerTime = firstTriggerTime;
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

    public Date getTriggerEndTime() {
        return triggerEndTime;
    }

    public void setTriggerEndTime(Date triggerEndTime) {
        this.triggerEndTime = triggerEndTime;
    }

    public Date generateNextTriggerTime() {
        if (Objects.isNull(this.cycleLevel)) {
            return null;
        }
        final GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(this.nextTriggerTime);
        CycleLevel cycleLevel = CycleLevel.getInstance(this.cycleLevel);
        switch (cycleLevel) {
            case MINUTE:
                cal.add(Calendar.MINUTE, cycleNum);
                break;
            case HOUR:
                cal.add(Calendar.HOUR, cycleNum);
                break;
            case DAY:
                cal.add(Calendar.DAY_OF_MONTH, cycleNum);
                break;
            case WEEK:
                cal.add(Calendar.WEEK_OF_MONTH, cycleNum);
                break;
            case MONTH:
                cal.add(Calendar.MONTH, cycleNum);
                break;
            case YEAR:
                cal.add(Calendar.YEAR, cycleNum);
                break;
        }
        return cal.getTime();
    }
}