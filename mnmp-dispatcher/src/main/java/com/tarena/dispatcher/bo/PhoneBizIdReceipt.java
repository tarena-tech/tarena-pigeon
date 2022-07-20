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

public class PhoneBizIdReceipt {
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
     * 回执状态
     */
    private Boolean success;



    public PhoneBizIdReceipt(String phone, String bizId) {
        this.phone = phone;
        this.bizId = bizId;
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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
