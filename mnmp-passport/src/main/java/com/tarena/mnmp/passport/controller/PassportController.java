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
import com.tarena.mnmp.passport.domain.LoginParam;
import com.tarena.mnmp.passport.service.PassportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "passport", tags = "用户中台")
@RestController
@RequestMapping("/passport")
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
    public String doLogin(@Valid @RequestBody LoginParam loginParam){
        return "";
    }

}
