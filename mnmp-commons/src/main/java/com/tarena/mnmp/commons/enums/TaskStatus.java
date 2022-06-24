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

package com.terena.mnmp.commons.enums;

public enum TaskStatus {
    TASK_NO_OPEN(0, "PREPARING"),
    TASK_DOING(1, "PUSHING"),
    TASK_STOP(2, "STOP"),
    TASK_END(3, "END");

    private int status;
    private String description;

    TaskStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public static TaskStatus getInstance(int status) {
        TaskStatus[] taskStatusEnums = values();
        for (TaskStatus auditStatusEnum : taskStatusEnums) {
            if (auditStatusEnum.status() == status) {
                return auditStatusEnum;
            }
        }
        return null;
    }

    public int status() {
        return this.status;
    }

    public String msg() {
        return this.description;
    }
}