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

package com.tarena.mnmp.passport.config;

import com.tarena.mnmp.passport.auth.impl.AuthenticatorImpl;
import com.tarena.mnmp.passport.exception.handler.MnmpAccessDeniedHanldler;
import com.tarena.mnmp.passport.exception.handler.MnmpAuthenticationEntryPoint;
import com.tarena.mnmp.passport.filter.MnmpAuthenticationFilter;
import com.tarena.mnmp.security.authentication.Authenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MnmpSecurityWebConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public MnmpAccessDeniedHanldler mnmpAccessDeniedHanldler(){
        return new MnmpAccessDeniedHanldler();
    }
    @Bean MnmpAuthenticationEntryPoint mnmpAuthenticationEntryPoint(){
        return new MnmpAuthenticationEntryPoint();
    }
    @Bean
    public Authenticator authenticator(){
        return new AuthenticatorImpl();
    }
    @Bean
    public MnmpAuthenticationFilter mnmpAuthenticationFilter(){
        MnmpAuthenticationFilter mnmpAuthenticationFilter=new MnmpAuthenticationFilter();
        mnmpAuthenticationFilter.setAuthenticator(authenticator());
        return mnmpAuthenticationFilter;
    }
    @Override protected void configure(HttpSecurity http) throws Exception {
        // 权限放行
        String[] permitList = {
            "/passport/login",
            "/passport/logout"
        };
        // 禁止跨域请求伪造过滤器
        http.csrf().disable();
        http.cors().disable();
        // 关闭session管理
        http.sessionManagement().disable();
        //关闭security内置用户名密码认证
        http.formLogin().disable();
        // 认证规则
        http.authorizeRequests()
            .antMatchers(permitList).permitAll()
            .anyRequest().authenticated();
        //权限不足处理器
        http.exceptionHandling().accessDeniedHandler(mnmpAccessDeniedHanldler());
        //未认证处理器
        http.exceptionHandling().authenticationEntryPoint(mnmpAuthenticationEntryPoint());
        //添加过滤器
        http.addFilterAfter(mnmpAuthenticationFilter(), SecurityContextPersistenceFilter.class);
    }

    @Override public void configure(WebSecurity web) throws Exception {
        String[] ignoreResources=
            {
                "/swagger-resources/**",    // Knife4j在线API文档的资源
                "/v2/api-docs/**",          // Knife4j在线API文档的资源
                "/favicon.ico",     // 网站图标文件
                "/",                // 根页面，通常是主页
                "/*.html",          // 任何html
                "/**/*.html",       // 任何目录下的html
                "/**/*.css",        // 任何目录下的css
                "/**/*.js"     // 任何目录下的js)
            };
        //静态页面,swagger页面不经过security过滤器
        web.ignoring().antMatchers(ignoreResources);


    }
}
