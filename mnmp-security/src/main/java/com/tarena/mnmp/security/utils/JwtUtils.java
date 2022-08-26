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

package com.tarena.mnmp.security.utils;

import com.alibaba.fastjson.JSON;
import com.tarena.mnmp.protocol.LoginToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class JwtUtils {
    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    //自定义载荷的key值
    private static final String CLAIM_KEY_USERNAME = "SUB";
    private static final String CLAIM_KEY_CREATED = "CREATED";

    public static LoginToken getLoginFromToken(String token, String secret, Long expiration) {
        //先解析荷载
        Claims claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
        String loginTokenJson = (String) claims.get(CLAIM_KEY_USERNAME);
        if (isTokenExpired(claims)) {
            return null;
        }
        //对比ip地址
        if (!StringUtils.isEmpty(loginTokenJson)) {
            return JSON.parseObject(loginTokenJson, LoginToken.class);
        } else {
            return null;
        }
    }

    private static boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        //返回判断时间前后
        return new Date().after(expiration);
    }

    public static String generateToken(LoginToken loginToken, String secret, Long expiration) {
        //准备jwt荷载对象claims,本质就是map,我们可以在里面自定义任意key和value
        Map<String, Object> claims = new HashMap<>();
        //存放日期时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        //存放user对象
        claims.put(CLAIM_KEY_USERNAME, JSON.toJSONString(loginToken));
        return generateTokenFromClaim(claims, secret, expiration);
    }

    private static String generateTokenFromClaim(Map<String, Object> claims, String secret, Long expiration) {
        JwtBuilder builder = Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate(claims, expiration))
            .signWith(SignatureAlgorithm.HS512, secret);
        return builder.compact();
    }

    public static Date generateExpirationDate(Map<String, Object> claims, Long expiration) {
        Date createTime = (Date) claims.get(CLAIM_KEY_CREATED);
        Long createTimeMili = createTime.getTime();
        Date expiredTime = new Date(createTimeMili + expiration);
        logger.info("荷载生成过期时间:{}", expiredTime.toGMTString());
        return expiredTime;
    }
}