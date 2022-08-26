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

public enum SmsTemplateType {
    ALL(0, "全部"),
    NOTICE(1, "短信通通知"),
    VALIDATE_CODE(2, "验证码"),
    PROMOTION(3, "推广短信");

    private int status;
    private String msg;

    SmsTemplateType(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static String getMsg(int status) {
        SmsTemplateType[] templateTypes = values();
        for (SmsTemplateType smsTemplateType : templateTypes) {
            if (smsTemplateType.status() == status) {
                return smsTemplateType.msg();
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
