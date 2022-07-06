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
 * Unless required byproviderlicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tarena.test.mnmp.admin.mapper.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarena.mnmp.admin.AdminApplication;
import com.tarena.mnmp.admin.mapper.provider.ProviderMapper;

import com.tarena.mnmp.commons.utils.Asserts;
import com.tarena.mnmp.constant.ErrorCode;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.provider.ProviderDO;
import com.tarena.mnmp.provider.ProviderDao;
import com.tarena.test.mnmp.admin.mapper.app.AppMapperTest;
import com.tarena.test.mnmp.admin.sql.app.AppSqlScript;
import com.tarena.test.mnmp.admin.sql.provider.ProviderSqlScript;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@SpringBootTest(classes = AdminApplication.class)
public class ProviderMapperTest {
    private static Logger logger = LoggerFactory.getLogger(ProviderMapperTest.class);
    @Autowired
    private ProviderDao providerMapper;
    /**
     * 查询
     */
    @Test
    @Sql(
        scripts = {ProviderSqlScript.TRUNCATE_TABLE, ProviderSqlScript.INSERT_TEST_DATA},
        config = @SqlConfig(encoding = "UTF-8")
    )
    @Sql(
        scripts = {ProviderSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void queryAllProvidersTest() throws BusinessException {
        List<ProviderDO> providers =
           providerMapper.queryAllProviders();
        logger.info("查询app总数:{}",providers.size());
        Asserts.isTrue(providers.size() != 2, new BusinessException(ErrorCode.SYSTEM_ERROR, "查询所有provider列表测试持久层mapper失败"));
    }
    @Test
    @Sql(
        scripts = {ProviderSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {ProviderSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void saveProviderTest() throws BusinessException {
        ProviderDO providerDO = new ProviderDO();
        providerDO.setCode("SMS_20220707");
        providerDO.setStatus(1);
        providerDO.setRemarks("测试供应商");
        providerDO.setName("ALI");
        providerDO.setContacts("zhangsan");
        providerDO.setNoticeType(1);
        providerDO.setOfficialWebsite("www.ali.com");
        providerDO.setPhone("18800099009");
        Integer result =providerMapper.save(providerDO);
        Asserts.isTrue(result == 0, new BusinessException(ErrorCode.SYSTEM_ERROR, "新增app应用测试持久层mapper失败"));
        logger.info("新增provider应用测试持久层mapper成功");
    }

    @Test
    @Sql(
        scripts = {ProviderSqlScript.TRUNCATE_TABLE, ProviderSqlScript.INSERT_TEST_DATA},
        config = @SqlConfig(encoding = "UTF-8")
    )
    @Sql(
        scripts = {ProviderSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void openAndCloseTest() throws BusinessException {
        ProviderDO providerDO1 =providerMapper.findById(1L);
        ProviderDO providerDO2 =providerMapper.findById(2L);
        Asserts.isTrue(providerDO1 == null ||providerDO2 == null, new BusinessException(ErrorCode.SYSTEM_ERROR, "id查询provider供应商测试持久层mapper失败"));
        Integer status1 =providerDO1.getStatus();
        Integer status2 =providerDO2.getStatus();
        Asserts.isTrue(status1 == 1 || status2 == 0, new BusinessException(ErrorCode.SYSTEM_ERROR, "测试provider数据有误,请检查前两条status属性是否是0,1"));
        providerMapper.enable(providerDO1.getId());
        providerMapper.disable(providerDO2.getId());
        providerDO1 =providerMapper.findById(1L);
        providerDO2 =providerMapper.findById(2L);
        status1 =providerDO1.getStatus();
        status2 =providerDO2.getStatus();
        Asserts.isTrue(status1 == 0 || status2 == 1, new BusinessException(ErrorCode.SYSTEM_ERROR, "开启关闭app应用测试持久层mapper失败"));
        logger.info("开启关闭provider供应商测试持久层mapper成功");
    }

    @Test
    @Sql(
        scripts = {ProviderSqlScript.TRUNCATE_TABLE, ProviderSqlScript.INSERT_TEST_DATA},
        config = @SqlConfig(encoding = "UTF-8")
    )
    @Sql(
        scripts = {ProviderSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void modifyProviderTest() throws BusinessException, JsonProcessingException {
        ProviderDO providerDO = providerMapper.findById(1L);
        logger.info("更新前供应商数据:{}",new ObjectMapper().writeValueAsString(providerDO));
        ProviderDO updateProviderDO = new ProviderDO();
        updateProviderDO.setId(1L);
        updateProviderDO.setCode("SMS_20220707");
        updateProviderDO.setStatus(1);
        updateProviderDO.setRemarks("测试供应商");
        updateProviderDO.setName("ALI");
        updateProviderDO.setContacts("zhangsan");
        updateProviderDO.setNoticeType(1);
        updateProviderDO.setOfficialWebsite("www.ali.com");
        updateProviderDO.setPhone("18800099009");
        Integer result =providerMapper.modify(updateProviderDO);
        Asserts.isTrue(result == 0, new BusinessException(ErrorCode.SYSTEM_ERROR, "更新provider供应商测试持久层mapper失败"));
        providerDO = providerMapper.findById(1L);
        logger.info("更新后供应商数据:{}",new ObjectMapper().writeValueAsString(providerDO));
        logger.info("更新provider供应商测试持久层mapper成功");
    }
}
