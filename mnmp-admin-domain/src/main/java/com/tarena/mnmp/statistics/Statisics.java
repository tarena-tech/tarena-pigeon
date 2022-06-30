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

package com.tarena.mnmp.statistics;



public class Statisics {

    private Long id;
    private Long type;
    private Long pushTime;
    private Long pushNumPlan;
    private Long pushNumReality;
    private Long pushSuccNum;
    private String pushSuccRate;
    private Long pushFailNum;
    private Long noReceiptNum;
    private String receiptRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getPushTime() {
        return pushTime;
    }

    public void setPushTime(Long pushTime) {
        this.pushTime = pushTime;
    }

    public Long getPushNumPlan() {
        return pushNumPlan;
    }

    public void setPushNumPlan(Long pushNumPlan) {
        this.pushNumPlan = pushNumPlan;
    }

    public Long getPushNumReality() {
        return pushNumReality;
    }

    public void setPushNumReality(Long pushNumReality) {
        this.pushNumReality = pushNumReality;
    }

    public Long getPushSuccNum() {
        return pushSuccNum;
    }

    public void setPushSuccNum(Long pushSuccNum) {
        this.pushSuccNum = pushSuccNum;
    }

    public String getPushSuccRate() {
        return pushSuccRate;
    }

    public void setPushSuccRate(String pushSuccRate) {
        this.pushSuccRate = pushSuccRate;
    }

    public Long getPushFailNum() {
        return pushFailNum;
    }

    public void setPushFailNum(Long pushFailNum) {
        this.pushFailNum = pushFailNum;
    }

    public Long getNoReceiptNum() {
        return noReceiptNum;
    }

    public void setNoReceiptNum(Long noReceiptNum) {
        this.noReceiptNum = noReceiptNum;
    }

    public String getReceiptRate() {
        return receiptRate;
    }

    public void setReceiptRate(String receiptRate) {
        this.receiptRate = receiptRate;
    }
}
