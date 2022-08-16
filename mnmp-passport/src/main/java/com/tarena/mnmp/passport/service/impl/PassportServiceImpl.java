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

import com.tarena.mnmp.passport.dao.RoleDO;
import com.tarena.mnmp.passport.dao.RoleMapper;
import com.tarena.mnmp.passport.dao.UserDO;
import com.tarena.mnmp.passport.dao.UserMapper;
import com.tarena.mnmp.passport.dao.UserRoleMapper;
import com.tarena.mnmp.passport.domain.LoginParam;
import com.tarena.mnmp.passport.domain.RegisterParam;
import com.tarena.mnmp.passport.domain.Token;
import com.tarena.mnmp.passport.domain.User;
import com.tarena.mnmp.passport.service.PassportService;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.security.LoginToken;
import com.tarena.mnmp.security.authentication.Authenticator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PassportServiceImpl implements PassportService {
    @Autowired
    private Authenticator authenticator;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
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
            throw new BusinessException("100", "用户名不存在");
        }
        boolean matches = passwordEncoder.matches(param.getPassword(), user.getPassword());
        if (!matches) {
            throw new BusinessException("100", "密码错误");
        }
        List<String> authorities = user.getAuthorities();
        loginToken.setAuthorities(authorities);
        loginToken.setId(user.getId());
        String tokenStr = authenticator.sign(loginToken, param.getPassword());
        String role = loginToken.getRole();
        return new Token(tokenStr, role);
    }

    @Transactional
    @Override public void doRegister(RegisterParam param) throws BusinessException {
        int count = userMapper.countByUsername(param.getUsername());
        if (count != 0) {
            throw new BusinessException("100", "用户名重复");
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(param, userDO);
        //加密
        userDO.setPassword(passwordEncoder.encode(param.getPassword()));
        //接收id
        int result = userMapper.save(userDO);
        log.info("拿到userId:{}", userDO.getId());
        if (result == 0) {
            throw new BusinessException("100", "注册用户新增失败");
        }
        RoleDO roleDO = new RoleDO();
        roleDO.setName("ROLE_"+param.getRole().toLowerCase());
        result = roleMapper.save(roleDO);
        if (result == 0) {
            throw new BusinessException("100", "注册角色新增失败");
        }
        result = userRoleMapper.save(userDO.getId(), roleDO.getId());
        if (result == 0) {
            throw new BusinessException("100", "注册用户角色关联失败");
        }
    }
}
