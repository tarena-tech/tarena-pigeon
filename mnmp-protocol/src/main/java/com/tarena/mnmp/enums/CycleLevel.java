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

public enum CycleLevel {

    MINUTE(0, "分钟"),
    HOUR(1, "小时"),
    DAY(2, "日"),
    WEEK(3, "周"),
    MONTH(4, "月"),
    YEAR(5, "年");

    public static CycleLevel getInstance(int level) {
        CycleLevel[] cycleLevels = values();
        for (CycleLevel cycleLevel : cycleLevels) {
            if (cycleLevel.getLevel() == level) {
                return cycleLevel;
            }
        }
        return HOUR;
    }

    private String description;
    private Integer level;

    CycleLevel(Integer level, String description) {
        this.description = description;
        this.level = level;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getLevel() {
        return level;
    }
}
