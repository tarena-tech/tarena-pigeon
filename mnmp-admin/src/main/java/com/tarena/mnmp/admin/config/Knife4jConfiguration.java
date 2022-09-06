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

package com.tarena.mnmp.admin.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.tarena.mnmp.protocol.LoginToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Knife4j（Swagger2）的配置
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    /**
     * 【重要】指定Controller包路径
     */
    private final String basePackage = "com.tarena.mnmp.admin.controller";
    /**
     * 分组名称
     */
    private final String groupName = "admin";
    /**
     * 主机名
     */
    private final String host = "http://www.tedu.cn";
    /**
     * 标题
     */
    private final String title = "信鸽-与非科技开源消息中台(Message Notify Middle Platform)-后台管理";
    /**
     * 简介
     */
    private final String description = "信鸽消息中台后台管理平台";
    /**
     * 服务条款URL
     */
    private final String termsOfServiceUrl = "http://www.apache.org/licenses/LICENSE-2.0";
    /**
     * 联系人
     */
    private final String contactName = "Charlie Show";
    /**
     * 联系网址
     */
    private final String contactUrl = "http://www.tedu.cn";
    /**
     * 联系邮箱
     */
    private final String contactEmail = "xiaoxw@tedu.cn";
    /**
     * 版本号
     */
    private final String version = "1.0.0-SNAPSHOT";

    @Autowired
    private OpenApiExtensionResolver openApiExtensionResolver;

    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .host(host)
            .apiInfo(apiInfo())
            .groupName(groupName)
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(PathSelectors.any())
            .build()
            .ignoredParameterTypes(LoginToken.class, HttpServletResponse.class, HttpServletRequest.class)
            .extensions(openApiExtensionResolver.buildExtensions(groupName));
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(title)
            .description(description)
            .termsOfServiceUrl(termsOfServiceUrl)
            .contact(new Contact(contactName, contactUrl, contactEmail))
            .version(version)
            .build();
    }
}
