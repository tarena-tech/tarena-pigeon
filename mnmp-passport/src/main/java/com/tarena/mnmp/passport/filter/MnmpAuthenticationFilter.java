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

package com.tarena.mnmp.passport.filter;

import com.tarena.mnmp.security.LoginToken;
import com.tarena.mnmp.security.authentication.Authenticator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class MnmpAuthenticationFilter extends OncePerRequestFilter {
    private Authenticator authenticator;

    /**
     * 从请求获取token做认证授权处理
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
        //获取token 这里我们要确定token的获取方式 可以在参数里,可以在头里
        String token = getRequestToken(request);
        if (StringUtils.isEmpty(token)){
            filterChain.doFilter(request,response);
            return;
        }
        //获取设备ip地址,绑定token使用
        String deviceIp = getIpAddress(request);
        LoginToken loginToken = authenticator.authenticate(token, deviceIp);
        if (ObjectUtils.isEmpty(loginToken)){
            filterChain.doFilter(request,response);
            return;
        }else{
            List<String> strAuthorities=loginToken.getAuthorities();
            List<GrantedAuthority> authorities=new ArrayList<>();
            for (String authority : strAuthorities) {
                SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(authority);
                authorities.add(simpleGrantedAuthority);
            }
            UsernamePasswordAuthenticationToken authentication=
                new UsernamePasswordAuthenticationToken(loginToken.getUsername(),null,authorities);
            SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            filterChain.doFilter(request,response);
        }

    }
    public String getRequestToken(HttpServletRequest request){
        //从Authorization头里获得
        String tokenHeader="Authorization";
        String tokenHeaderPrefix="Bearer 11";
        String tokenParam="token";
        String token = request.getParameter(tokenParam);
        String tokenHeaderValue="";
        if (StringUtils.isEmpty(token)){
            tokenHeaderValue = request.getHeader(tokenHeader);
        }
        if ((!StringUtils.isEmpty(tokenHeaderValue))&&tokenHeaderValue.startsWith(tokenHeaderPrefix)){
            token=tokenHeader.substring(tokenHeaderPrefix.length()+1);
        }
        return token;
    }
    public  String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }
}