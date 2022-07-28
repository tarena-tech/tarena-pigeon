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
package com.tarena.mnmp.commons.utils;

import java.util.HashSet;
import java.util.Set;
import org.springframework.util.StringUtils;

public class ExcelUtils {

    final static Set<String> EXCEL_SUFFIXS = new HashSet<>();

    static {
        EXCEL_SUFFIXS.add("xls");
        EXCEL_SUFFIXS.add("xlsx");
    }


    public static boolean checkExcel(String name) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }

        int index = name.indexOf(".");
        if (index == -1) {
            return false;
        }
        return EXCEL_SUFFIXS.contains(name.substring(index + 1));
    }

    public static String suffixName(String name) {
        return name.substring(name.indexOf(".") + 1);
    }

}
