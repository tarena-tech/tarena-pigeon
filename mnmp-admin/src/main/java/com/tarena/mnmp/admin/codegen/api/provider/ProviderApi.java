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

package com.tarena.mnmp.admin.codegen.api.provider;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tarena.mnmp.admin.controller.provider.ProviderView;
import com.tarena.mnmp.admin.param.AuditParam;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.provider.ProviderQueryParam;
import com.tarena.mnmp.domain.provider.ProviderSaveParam;
import com.tarena.mnmp.protocol.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
@Api(
    value = "Provider",
    tags = "供应商管理"
)
@RequestMapping("provider")
public interface ProviderApi {
    /**
     * 新增服务商
     *
     * @param providerSaveParam
     */
    @ApiOperationSupport(order = 2001)
    @ApiOperation(
        value = "新增服务商",
        nickname = "addProvider",
        notes = ""
    )
    @PostMapping(
        value = {"/add"},
        consumes = {"application/json"}
    )
    @Deprecated
    void addProvider(
        @ApiParam(value = "新增服务商", required = true) @Valid @RequestBody ProviderSaveParam providerSaveParam);

    @ApiOperationSupport(order = 2003)
    @ApiOperation(
        value = "修改服务商信息",
        nickname = "editProvider",
        notes = ""
    )
    @PostMapping(
        value = {"/edit"},
        consumes = {"application/json"}
    )
    @Deprecated
    void editProvider(@ApiParam(value = "修改服务商信息", required = true) @Valid @RequestBody ProviderSaveParam providerSaveParam);

    @ApiOperation(value = "保存服务商 创建/更新")
    @PostMapping("save")
    void save(@RequestBody ProviderSaveParam param);

    @ApiOperationSupport(order = 2002)
    @ApiOperation(
        value = "关闭使用服务商",
        nickname = "closeProvider",
        notes = ""
    )
    @PostMapping({"/close"})
    @Deprecated
    void closeProvider(
        @NotNull @ApiParam(value = "要关闭的服务商id", required = true) @Valid @RequestParam(value = "id", required = true) Long id);

    @ApiOperationSupport(order = 2004)
    @ApiOperation(
        value = "开启服务商",
        nickname = "openProvider",
        notes = ""
    )
    @PostMapping({"/open"})
    @Deprecated
    void openProvider(
        @NotNull @ApiParam(value = "要开启的服务商id", required = true) @Valid @RequestParam(value = "id", required = true) Long id);

    @ApiOperationSupport(order = 2004)
    @ApiOperation(value = "切换服务商可用状态")
    @PostMapping({"/change/enable/status"})
    void changeEnableStatus(@NotNull @ApiParam(value = "服务商id", required = true) @Valid @RequestParam(value = "id", required = true) Long id) throws BusinessException;



    @ApiOperationSupport(order = 2005)
    @ApiOperation(
        value = "查询服务分页商列表",
        nickname = "queryPage",
        notes = ""
    )
    @GetMapping(value = {"/query/page"})
    PagerResult<ProviderView> queryPage(ProviderQueryParam param);

    @ApiOperationSupport(order = 2006)
    @ApiOperation(
        value = "查看服务商详情",
        nickname = "queryProviderDetail",
        notes = "",
        response = ProviderView.class
    )
    @GetMapping(
        value = {"/query/detail"},
        produces = {"application/json"}
    )
    ProviderView queryProviderDetail(
        @NotNull @ApiParam(value = "服务商id", required = true) @Valid @RequestParam(value = "id", required = true) Long id);

    /**
     * 审核应用
     */
    @ApiOperationSupport(order = 2007)
    @ApiOperation(
        value = "审核供应商",
        notes = ""
    )
    @PostMapping(
        value = "/audit"
    )
    void auditProvider(@RequestBody AuditParam param);

    @ApiOperationSupport(order = 2008)
    @ApiOperation(
        value = "供应商列表",
        notes = ""
    )
    @GetMapping(
        value = "/query/list"
    )
    List<ProviderView> queryList(ProviderQueryParam param);

}
