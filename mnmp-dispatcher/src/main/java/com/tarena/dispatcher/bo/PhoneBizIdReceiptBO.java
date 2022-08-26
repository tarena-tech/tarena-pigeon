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

package com.tarena.dispatcher.bo;

import java.util.Date;

public class PhoneBizIdReceiptBO {
    /**
     * 任务ID
     */
    private Long taskId;
    /**
     * phone number
     */
    private String phone;
    /**
     * biz id
     */
    private String bizId;
    /**
     * provider cost sms numbers
     */
    private Integer costNumbers;

    /**
     * 接收时间
     */
    private Date receiveTime;
    /**
     * 消息发送状态 0发送给供应商失败 1发送给供应商成功 2发送给目标失败 3发送给目标成功
     */
    private Integer status;

    /**
     * 发送结果
     */
    private String sendResult;

    public PhoneBizIdReceiptBO(String phone, String bizId, Long taskId) {
        this.phone = phone;
        this.bizId = bizId;
        this.taskId = taskId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Integer getCostNumbers() {
        return costNumbers;
    }

    public void setCostNumbers(Integer costNumbers) {
        this.costNumbers = costNumbers;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }
}
