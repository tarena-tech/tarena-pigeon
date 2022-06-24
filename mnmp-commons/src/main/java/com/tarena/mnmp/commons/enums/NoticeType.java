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

package com.tarena.mnmp.commons.enums;

public enum NoticeType {
    SMS(1, "Short Messaging Service"),
    EMAIL(2, "email"),
    WECHAT(3, " enterprise wechat");

    private int type;
    private String description;

    NoticeType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public static NoticeType getEnum(int type) {
        NoticeType[] noticeTypeEnums = values();
        for (NoticeType noticeTypeEnum : noticeTypeEnums) {
            if (noticeTypeEnum.type() == type) {
                return noticeTypeEnum;
            }
        }
        return null;
    }

    public int type() {
        return type;
    }

    public String getDescription() {
        return description;
    }

}
