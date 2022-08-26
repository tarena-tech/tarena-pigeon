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

package com.tarena.mnmp.enums;

public enum TargetStatus {
    SENT_FAIL(0, "发送供应商失败"),
    SENT_TO_PROVIDER(1, "发送供应商成功"),
    SENT_TARGET_FAIL(2, "发送目标失败"),
    SENT_TO_TARGET(3, "发送目标成功");

    private int status;
    private String msg;

    TargetStatus(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static TargetStatus getStatusEnum(int status) {
        TargetStatus[] targetStatusEnums = values();
        for (TargetStatus statusEnum : targetStatusEnums) {
            if (statusEnum.status() == status) {
                return statusEnum;
            }
        }
        return null;
    }

    public int status() {
        return this.status;
    }

    public String msg() {
        return this.msg;
    }
}
