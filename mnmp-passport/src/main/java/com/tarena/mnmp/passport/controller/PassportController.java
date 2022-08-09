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

package com.tarena.mnmp.passport.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tarena.mnmp.commons.utils.Asserts;
import com.tarena.mnmp.passport.domain.LoginParam;
import com.tarena.mnmp.passport.service.PassportService;
import com.tarena.mnmp.passport.utils.IPUtils;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "passport", tags = "用户中台")
@RestController
@RequestMapping("/passport")
@Slf4j
public class PassportController {

    @Autowired
    private PassportService passportService;

    @ApiOperationSupport(order = 1)
    @ApiOperation(
        value = "登录",
        nickname = "login",
        notes = "",
        response = String.class
    )
    @PostMapping("/login")
    public Result<String> doLogin(@Valid @RequestBody LoginParam loginParam,
        HttpServletRequest request) throws BusinessException {
        String address = IPUtils.getIpAddress(request);
        Asserts.isTrue(address == null, new BusinessException("100", "无法获取请求路径"));
        log.info("登录设备ip地址:{}", address);
        String token = passportService.doLogin(loginParam, address);
        Asserts.isTrue(token == null || token.length() == 0, new BusinessException("100", "用户名不存在或者密码不正确"));

        return new Result<>(token);
    }

}
