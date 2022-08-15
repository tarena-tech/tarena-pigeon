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

package com.tarena.mnmp.passport.service.impl;

import com.tarena.mnmp.passport.domain.LoginParam;
import com.tarena.mnmp.passport.domain.RegisterParam;
import com.tarena.mnmp.passport.service.PassportService;
import com.tarena.mnmp.security.LoginToken;
import com.tarena.mnmp.security.authentication.Authenticator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassportServiceImpl implements PassportService {
    @Autowired
    private Authenticator authenticator;

    @Override public String doLogin(LoginParam param, String deviceIp) {
        //转化对象
        LoginToken loginToken = new LoginToken();
        BeanUtils.copyProperties(param, loginToken);
        loginToken.setDeviceIp(deviceIp);
        return authenticator.sign(loginToken, param.getPassword());
    }

    @Override public void doRegister(RegisterParam param) {

    }
}
