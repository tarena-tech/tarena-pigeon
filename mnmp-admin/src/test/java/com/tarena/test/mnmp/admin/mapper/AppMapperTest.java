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

package com.tarena.test.mnmp.admin.mapper;

import com.tarena.mnmp.admin.AdminApplication;
import com.tarena.mnmp.admin.mapper.AppMapper;
import com.tarena.mnmp.app.App;
import com.tarena.mnmp.commons.utils.Asserts;
import com.tarena.mnmp.constant.ErrorCode;
import com.tarena.mnmp.enums.StatusRecord;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.test.mnmp.admin.sql.AppSqlScript;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@SpringBootTest(classes = {AdminApplication.class})
public class AppMapperTest {
    private static Logger logger = LoggerFactory.getLogger(AppMapperTest.class);
    @Autowired
    private AppMapper appMapper;

    @Test
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_APP_TABLE,AppSqlScript.INSERT_TEST_DATA},
        config=@SqlConfig(encoding = "UTF-8")
    )
    public void queryAllAppsTest() {
        List<App> apps =
            appMapper.queryAllApps(StatusRecord.ENABLE);
        logger.info("查询app总数:{}",apps.size());
    }
    @Test
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_APP_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_APP_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void saveAppTest() throws BusinessException {
        App app=new App();
        app.setTeamMembers("wang,li,zhang,zhao");
        app.setStatus(1);
        app.setRemarks("测试程序");
        app.setName("达内测试");
        app.setLeader("ceshiwang");
        app.setCode("CODE_TEST_001");
        Integer result = appMapper.save(app);
        Asserts.isTrue(result==0,new BusinessException(ErrorCode.SYSTEM_ERROR,"新增app应用测试持久层mapper失败"));
        logger.info("新增app应用测试持久层mapper成功");
    }

}
