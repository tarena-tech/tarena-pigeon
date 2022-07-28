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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "task入参")
public class TaskParam {

    @ApiModelProperty(value = "主键id, 修改时必填", required = false)
    private Long id;

    @ApiModelProperty(value = "任务名称", required = true)
    @NotBlank(message = "任务名称为必填项")
    private String name;

    @ApiModelProperty(value = "任务类型 0:立即 1:定时 2:周期 3:条件规则触发", required = true)
    @NotNull(message = "任务类型为必填项")
    private Integer taskType;

    @ApiModelProperty(value = "通知类型： 1-sms,2-email,3-wechat", required = true)
    @NotNull(message = "通知类型为必填项")
    private Integer noticeType;

    @ApiModelProperty(value = "绑定的模板id，从模板列表拉取", required = true)
    @NotNull(message = "绑定的模板id为必填项")
    private Long templateId;

    @ApiModelProperty(value = "签名id，从签名列表拉取", required = true)
    @NotNull(message = "签名id为必填项")
    private Long signId;

    @ApiModelProperty(value = "appId 从下拉列表获取", required = true)
    @NotNull(message = "appId为必填项")
    private Long appId;

    @ApiModelProperty(value = "周期类型 1:小时 2:日 3:周 4:月 5:年", required = true)
    @NotNull(message = "周期类型为必填项")
    private Integer cycleLevel;

    @ApiModelProperty(value = "周期数", required = true)
    @NotNull(message = "周期数为必填项")
    private Integer cycleNum;


    @ApiModelProperty(value = "创建人", required = true)
    @NotBlank(message = "创建人为必填项")
    private String creatorName;


    @ApiModelProperty(value = "结束时间", required = true)
    @NotNull(message = "结束时间为必填项")
    private Date triggerEndTime;

    @ApiModelProperty(value = "文件路径", required = true)
    @NotBlank(message = "文件路径为必填项")
    private String filePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getTriggerEndTime() {
        return triggerEndTime;
    }

    public void setTriggerEndTime(Date triggerEndTime) {
        this.triggerEndTime = triggerEndTime;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}
