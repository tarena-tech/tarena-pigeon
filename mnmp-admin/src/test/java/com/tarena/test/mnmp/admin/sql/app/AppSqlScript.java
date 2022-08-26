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

package com.tarena.test.mnmp.admin.sql.app;

public class AppSqlScript {
    /**
     * 清空所有数据表的SQL脚本
     */
    public static final String TRUNCATE_TABLE = "classpath:sql/app/truncate_app_test_data.sql";

    /**
     * 向所有数据表中插入测试数据的SQL脚本
     */
    public static final String INSERT_TEST_DATA = "classpath:sql/app/insert_app_test_data.sql";

}
