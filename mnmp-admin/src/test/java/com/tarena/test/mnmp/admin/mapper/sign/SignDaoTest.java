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

package com.tarena.test.mnmp.admin.mapper.sign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarena.mnmp.admin.AdminApplication;
import com.tarena.mnmp.commons.utils.Asserts;
import com.tarena.mnmp.constant.ErrorCode;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.sign.SignDO;
import com.tarena.mnmp.sign.SignDao;
import com.tarena.mnmp.sign.SignQuery;
import com.tarena.test.mnmp.admin.sql.sign.SignSqlScript;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@SpringBootTest(classes = {AdminApplication.class})
public class SignDaoTest {
    private static Logger logger = LoggerFactory.getLogger(SignDaoTest.class);
    @Autowired
    private SignDao signDao;

    /**
     * 查询
     */
    @Test
    @Sql(
        scripts = {SignSqlScript.TRUNCATE_TABLE, SignSqlScript.INSERT_TEST_DATA},
        config = @SqlConfig(encoding = "UTF-8")
    )
    @Sql(
        scripts = {SignSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void queryAllProvidersTest() throws BusinessException {
        SignQuery signQuery = new SignQuery();
        signQuery.setName("童程");
        signQuery.setAppCode("tc");
        signQuery.setAuditStatus(1);
        List<SignDO> signDOs = signDao.querySigns(signQuery);
        logger.info("查询签名总数:{}", signDOs.size());
        Asserts.isTrue(signDOs.size() != 2, new BusinessException(ErrorCode.SYSTEM_ERROR, "查询条件Sign签名列表测试持久层mapper失败"));
    }

    @Test
    @Sql(
        scripts = {SignSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {SignSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void saveProviderTest() throws BusinessException {
        SignDO signDO = new SignDO();
        signDO.setAppCode("tctm");
        signDO.setAuditStatus(0);
        signDO.setEnabled(0);
        signDO.setName("童程童美");
        signDO.setCreator("100011");
        signDO.setAppId(1L);
        signDO.setAppName("童程童美");
        signDO.setRemarks("童程童美签名");
        Integer result = signDao.save(signDO);
        Asserts.isTrue(result == 0, new BusinessException(ErrorCode.SYSTEM_ERROR, "新增签名测试持久层mapper失败"));
        logger.info("新增签名测试持久层mapper成功");
    }

    @Test
    @Sql(
        scripts = {SignSqlScript.TRUNCATE_TABLE, SignSqlScript.INSERT_TEST_DATA},
        config = @SqlConfig(encoding = "UTF-8")
    )
    @Sql(
        scripts = {SignSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void openAndCloseTest() throws BusinessException {
        SignDO signDO1 = signDao.findById(1L);
        SignDO signDO2 = signDao.findById(2L);
        Asserts.isTrue(signDO1 == null || signDO2 == null, new BusinessException(ErrorCode.SYSTEM_ERROR, "id查询签名测试持久层mapper失败"));
        Integer enabled1 = signDO1.getEnabled();
        Integer enabled2 = signDO2.getEnabled();
        Asserts.isTrue(enabled1 == 1 || enabled2 == 0, new BusinessException(ErrorCode.SYSTEM_ERROR, "测试签名数据有误,请检查前两条enabled属性是否是0,1"));
        signDao.enable(signDO1.getId());
        signDao.disable(signDO2.getId());
        signDO1 = signDao.findById(1L);
        signDO2 = signDao.findById(2L);
        enabled1 = signDO1.getEnabled();
        enabled2 = signDO2.getEnabled();
        Asserts.isTrue(enabled1 == 0 || enabled2 == 1, new BusinessException(ErrorCode.SYSTEM_ERROR, "开启关闭app应用测试持久层mapper失败"));
        logger.info("开启关闭provider供应商测试持久层mapper成功");
    }

    @Test
    @Sql(
        scripts = {SignSqlScript.TRUNCATE_TABLE, SignSqlScript.INSERT_TEST_DATA},
        config = @SqlConfig(encoding = "UTF-8")
    )
    @Sql(
        scripts = {SignSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void modifyProviderTest() throws BusinessException, JsonProcessingException {
        SignDO signDO = signDao.findById(1L);
        logger.info("更新前供应商数据:{}", new ObjectMapper().writeValueAsString(signDO));
        SignDO updateSignDO = new SignDO();
        updateSignDO.setAppCode("tctm");
        updateSignDO.setAuditStatus(0);
        updateSignDO.setEnabled(0);
        updateSignDO.setName("童程童美");
        updateSignDO.setCreator("100011");
        updateSignDO.setAppId(1L);
        updateSignDO.setAppName("童程童美");
        updateSignDO.setRemarks("童程童美签名");
        updateSignDO.setId(signDO.getId());
        Integer result = signDao.modify(updateSignDO);
        Asserts.isTrue(result == 0, new BusinessException(ErrorCode.SYSTEM_ERROR, "更新provider供应商测试持久层mapper失败"));
        signDO = signDao.findById(1L);
        logger.info("更新后供应商数据:{}", new ObjectMapper().writeValueAsString(signDO));
        logger.info("更新provider供应商测试持久层mapper成功");
    }
}
