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

package com.tarena.mnmp.admin.security.filter;

import com.tarena.mnmp.admin.utils.IPUtils;
import com.tarena.mnmp.protocol.LoginToken;
import com.tarena.mnmp.security.authentication.Authenticator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class MnmpAuthenticationFilter extends OncePerRequestFilter {
    private Authenticator authenticator;
    private static final AntPathRequestMatcher DEFAULT_PASS_LOGIN_REQUEST = new AntPathRequestMatcher("/passport/login",
        "POST");

    /**
     * 从请求获取token做认证授权处理
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        //清空当前认证数据 理论上来说,如果关闭sessionManagement使用,可以不需要
        SecurityContextHolder.clearContext();
        //放行login
        if (isLoginRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        //获取token 这里我们要确定token的获取方式 可以在参数里,可以在头里
        String token = getRequestToken(request);
        if (StringUtils.isEmpty(token)) {
            //空token说明没有认证,不赋值权限
            filterChain.doFilter(request, response);
            return;
        }
        //获取设备ip地址,绑定token使用
        String deviceIp = IPUtils.getIpAddress(request);
        log.info("ip: {}", deviceIp);
        log.info("请求地址：{}", request.getRequestURL());
        LoginToken loginToken = authenticator.authenticate(token, deviceIp);
        log.info("token: {}", loginToken);
        if (ObjectUtils.isEmpty(loginToken)) {
            log.info("认证失败 客户端ip:{}", deviceIp);
            log.info("token中的ip: {}", loginToken.getDeviceIp());
            filterChain.doFilter(request, response);
        } else {
            log.info("认证成功 客户端ip:{}", deviceIp);
            log.info("认证成功，token中的ip: {}", loginToken.getDeviceIp());
            request.setAttribute("admin_token", loginToken);
            List<String> strAuthorities = loginToken.getAuthorities();
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String authority : strAuthorities) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
                authorities.add(simpleGrantedAuthority);
            }
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(loginToken.getUsername(), loginToken, authorities);
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            filterChain.doFilter(request, response);
        }

    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return DEFAULT_PASS_LOGIN_REQUEST.matches(request);
    }

    ;

    public String getRequestToken(HttpServletRequest request) {
        //从Authorization头里获得
        String tokenHeader = "Authorization";
        String tokenHeaderPrefix = "Bearer ";
        String tokenParam = "token";
        String token = request.getParameter(tokenParam);
        String tokenHeaderValue = "";
        if (StringUtils.isEmpty(token)) {
            tokenHeaderValue = request.getHeader(tokenHeader);
        }
        if (!StringUtils.isEmpty(tokenHeaderValue) && tokenHeaderValue.startsWith(tokenHeaderPrefix)) {
            token = tokenHeaderValue.substring(tokenHeaderPrefix.length());
        }
        return token;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }
}
