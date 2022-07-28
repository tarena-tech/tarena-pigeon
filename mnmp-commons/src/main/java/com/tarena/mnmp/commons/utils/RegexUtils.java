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

import java.util.regex.Pattern;

public class RegexUtils {


    static final String PHONE_REGEX = "^\\d{11}$";
    static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX, Pattern.CASE_INSENSITIVE);

    public static boolean checkPhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

}
