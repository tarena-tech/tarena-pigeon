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

import com.tarena.mnmp.passport.dao.UserMapper;
import com.tarena.mnmp.passport.domain.LoginParam;
import com.tarena.mnmp.passport.domain.RegisterParam;
import com.tarena.mnmp.passport.domain.Token;
import com.tarena.mnmp.passport.domain.User;
import com.tarena.mnmp.passport.service.PassportService;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.security.LoginToken;
import com.tarena.mnmp.security.authentication.Authenticator;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PassportServiceImpl implements PassportService {
    @Autowired
    private Authenticator authenticator;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override public Token doLogin(LoginParam param, String deviceIp) throws BusinessException {
        //转化对象
        LoginToken loginToken = new LoginToken();
        BeanUtils.copyProperties(param, loginToken);
        loginToken.setDeviceIp(deviceIp);
        String username = param.getUsername();
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new BusinessException("100","用户名不存在");
        }
        boolean matches = passwordEncoder.matches(param.getPassword(), user.getPassword());
        if (!matches) {
            throw new BusinessException("100","密码错误");
        }
        List<String> authorities = user.getAuthorities();
        loginToken.setAuthorities(authorities);
        String tokenStr = authenticator.sign(loginToken, param.getPassword());
        String role = loginToken.getRole();
        return new Token(tokenStr,role);
    }

    @Override public void doRegister(RegisterParam param) {

    }
}
