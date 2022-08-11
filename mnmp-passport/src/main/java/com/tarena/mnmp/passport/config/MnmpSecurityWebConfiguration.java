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

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MnmpSecurityWebConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MnmpAccessDeniedHanldler mnmpAccessDeniedHanldler() {
        return new MnmpAccessDeniedHanldler();
    }

    @Bean MnmpAuthenticationEntryPoint mnmpAuthenticationEntryPoint() {
        return new MnmpAuthenticationEntryPoint();
    }

    @Bean
    public Authenticator authenticator() {
        return new AuthenticatorImpl();
    }

    @Bean
    public MnmpAuthenticationFilter mnmpAuthenticationFilter() {
        MnmpAuthenticationFilter mnmpAuthenticationFilter = new MnmpAuthenticationFilter();
        mnmpAuthenticationFilter.setAuthenticator(authenticator());
        return mnmpAuthenticationFilter;
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");    //允许的请求方法，PSOT、GET等
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**", corsConfiguration); //配置允许跨域访问的url
        return source;
    }
    @Override protected void configure(HttpSecurity http) throws Exception {
        // 权限放行
        String[] permitList = {
            "/passport/login",
            "/passport/logout"
        };
        // 禁止跨域请求伪造过滤器
        http.csrf().disable();
        http.cors().configurationSource(corsConfigurationSource());

        // 关闭session管理
        http.sessionManagement().disable();
        //关闭security内置用户名密码认证
        http.formLogin().disable();
        // 认证规则
        http.authorizeRequests()
            .antMatchers(permitList).permitAll()
            .antMatchers("/authorize/test2").hasRole("admin")
            .anyRequest().authenticated();
        //权限不足处理器
        http.exceptionHandling().accessDeniedHandler(mnmpAccessDeniedHanldler());
        //未认证处理器
        http.exceptionHandling().authenticationEntryPoint(mnmpAuthenticationEntryPoint());
        //添加过滤器
        http.addFilterBefore(mnmpAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override public void configure(WebSecurity web) throws Exception {
        //静态页面,swagger页面不经过security过滤器
        web.ignoring().antMatchers(
            "/swagger-resources/**",    // Knife4j在线API文档的资源
            "/v2/api-docs/**",          // Knife4j在线API文档的资源
            "/favicon.ico",     // 网站图标文件
            "/",                // 根页面，通常是主页
            "/*.html",          // 任何html
            "/**/*.html",       // 任何目录下的html
            "/**/*.css",        // 任何目录下的css
            "/**/*.js");

    }
}
