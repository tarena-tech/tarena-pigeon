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

package com.tarena.mnmp.admin.security.auth.impl;

import com.tarena.mnmp.admin.security.config.JwtConfiguration;
import com.tarena.mnmp.protocol.LoginToken;
import com.tarena.mnmp.security.authentication.Authenticator;
import com.tarena.mnmp.security.utils.JwtUtils;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class AuthenticatorImpl implements Authenticator {
    @Autowired private JwtConfiguration jwtConfig;
    @Autowired private PasswordEncoder passwordEncoder;
    private Map<String, String> tokenToDeviceIp = new HashMap<>();

    @Override public String sign(LoginToken login, String password) {
        return "sign";
    }

    @Override public LoginToken authenticate(String token, String deviceIp) {
        LoginToken login = JwtUtils.getLoginFromToken(token, jwtConfig.getJwtSecret(), jwtConfig.getExpiration());

        if (null == login) {
            return null;
        }

        if (null == login.getDeviceIp() || !deviceIp.equals(login.getDeviceIp())) {
            return null;
        }

        return login;
    }

}
