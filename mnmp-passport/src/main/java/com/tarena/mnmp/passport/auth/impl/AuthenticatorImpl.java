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

package com.tarena.mnmp.passport.auth.impl;

import com.tarena.mnmp.passport.config.JwtConfiguration;
import com.tarena.mnmp.passport.dao.UserMapper;
import com.tarena.mnmp.passport.domain.User;
import com.tarena.mnmp.security.utils.JwtUtils;
import com.tarena.mnmp.security.LoginToken;
import com.tarena.mnmp.security.authentication.Authenticator;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticatorImpl implements Authenticator {
    @Autowired
    private JwtConfiguration jwtConfig;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override public String sign(LoginToken login, String password) {
        String username = login.getUsername();
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return null;
        }
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches) {
            return null;
        }
        login.setAuthorities(user.getAuthorities());
        //认证成功,生成token返回
        return JwtUtils.generateToken(login, jwtConfig.getJwtSecret(), jwtConfig.getExpiration());
    }

    @Override public LoginToken authenticate(String token, String deviceIp) {
        LoginToken login = JwtUtils.getLoginFromToken(token, jwtConfig.getJwtSecret(), jwtConfig.getExpiration());
        if (login.getDeviceIp() == null || !login.getDeviceIp().equals(deviceIp)) {
            return null;
        }
        return login;
    }

}
