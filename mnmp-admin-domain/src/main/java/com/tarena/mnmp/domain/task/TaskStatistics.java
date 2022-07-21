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

package com.tarena.mnmp.domain.task;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "任务统计")
public class TaskStatistics {

    private Long id;

    private Integer pushNum;

    private Integer pushSuccNum;

    private Integer pushFailNum;

    private String pushSuccRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPushNum() {
        return pushNum;
    }

    public void setPushNum(Integer pushNum) {
        this.pushNum = pushNum;
    }

    public Integer getPushSuccNum() {
        return pushSuccNum;
    }

    public void setPushSuccNum(Integer pushSuccNum) {
        this.pushSuccNum = pushSuccNum;
    }

    public Integer getPushFailNum() {
        return pushFailNum;
    }

    public void setPushFailNum(Integer pushFailNum) {
        this.pushFailNum = pushFailNum;
    }

    public String getPushSuccRate() {
        return pushSuccRate;
    }

    public void setPushSuccRate(String pushSuccRate) {
        this.pushSuccRate = pushSuccRate;
    }
}
