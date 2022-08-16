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

package com.tarena.mnmp.admin.codegen.api.app;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tarena.mnmp.admin.annotation.User;
import com.tarena.mnmp.admin.controller.app.AppView;
import com.tarena.mnmp.admin.param.AuditParam;
import com.tarena.mnmp.domain.app.AppQueryParam;
import com.tarena.mnmp.domain.app.AppSaveParam;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.security.LoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 统一了后台管理中应用C端管理功能接口,添加knife4j增强swagger接口文件生成
 */
@Validated
@Api(value = "app", tags = "应用管理")
@RequestMapping("/app")
public interface AppApi {
    /**
     * 新增应用
     * @param appSaveParam 新增应用参数封装对象
     * @param token 授权token,包含用户id 权限集合 角色
     */
    @ApiOperationSupport(order = 1001)
    @ApiOperation(value = "新增/修改 应用")
    @PostMapping("save")
    @ApiParam(name = "appSaveParam", value = "新增应用", required = true)
    @PreAuthorize("hasAnyRole('admin', 'root', 'user')")
    void save(@Valid @RequestBody AppSaveParam appSaveParam, @User LoginToken token) throws BusinessException;

    /**
     * 修改应用发布状态:审核通过,审核不通过
     * @param id 应用id
     * @param token 授权token,包含用户id 权限集合 角色
     * @throws BusinessException
     */
    @ApiOperationSupport(order = 1004)
    @ApiOperation(value = "更改可用状态")
    @PostMapping("change/enable/status")
    @PreAuthorize("hasAnyRole('admin','root', 'user')")
    void changeEnableStatus(Long id , @User LoginToken token) throws BusinessException;

    /**
     * 查看应用详情
     *
     * @param id
     */
    @ApiOperationSupport(order = 1005)
    @ApiOperation(value = "查看应用详情")
    @GetMapping("query/detail")
    @PreAuthorize("hasAnyRole('admin','root','user')")
    AppView queryAppDetail(
        @NotNull @ApiParam(value = "应用id", required = true) @Valid @RequestParam(value = "id", required = true) Long id);

    /**
     * 查询应用管理列表
     */
    @ApiOperationSupport(order = 1006)
    @ApiOperation(value = "查询应用管理分页列表")
    @GetMapping("query/page")
    @PreAuthorize("hasAnyRole('admin','root','user')")
    PagerResult<AppView> queryPage(AppQueryParam param);

    @ApiOperationSupport(order = 1006)
    @ApiOperation(value = "查询应用管理列表")
    @GetMapping("query/list")
    @PreAuthorize("hasAnyRole('admin','root','user')")
    List<AppView> queryList(AppQueryParam param);

    /**
     * 审核应用
     */
    @ApiOperationSupport(order = 1007)
    @ApiOperation(value = "审核应用")
    @PostMapping("audit")
    @PreAuthorize("hasAnyRole('admin','root')")
    void auditApp(@RequestBody AuditParam param);
}
