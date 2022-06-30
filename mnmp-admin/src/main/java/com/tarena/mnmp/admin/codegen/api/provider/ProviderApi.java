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
import com.tarena.mnmp.provider.Provider;
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
@RequestMapping("/provider")
public interface ProviderApi {
    /**
     * 新增服务商
     *
     * @param provider
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
    void addProvider(@ApiParam(value = "新增服务商", required = true) @Valid @RequestBody Provider provider);

    @ApiOperationSupport(order = 2002)
    @ApiOperation(
        value = "关闭使用服务商",
        nickname = "closeProvider",
        notes = ""
    )
    @PostMapping({"/close"})
    void closeProvider(
        @NotNull @ApiParam(value = "要关闭的服务商id", required = true) @Valid @RequestParam(value = "id", required = true) Long id);

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
    void editProvider(@ApiParam(value = "修改服务商信息", required = true) @Valid @RequestBody Provider provider);

    @ApiOperationSupport(order = 2004)
    @ApiOperation(
        value = "开启服务商",
        nickname = "openProvider",
        notes = ""
    )
    @PostMapping({"/open"})
    void openProvider(
        @NotNull @ApiParam(value = "要开启的服务商id", required = true) @Valid @RequestParam(value = "id", required = true) Long id);

    @ApiOperationSupport(order = 2005)
    @ApiOperation(
        value = "查询服务商列表",
        nickname = "queryList",
        notes = "",
        response = Provider.class,
        responseContainer = "List"
    )
    @GetMapping(
        value = {"/queryList"},
        produces = {"application/json"}
    )
    List<Provider> queryList();

    @ApiOperationSupport(order = 2006)
    @ApiOperation(
        value = "查看服务商详情",
        nickname = "queryProviderDetail",
        notes = "",
        response = Provider.class
    )
    @GetMapping(
        value = {"/queryDetail"},
        produces = {"application/json"}
    )
    Provider queryProviderDetail(
        @NotNull @ApiParam(value = "服务商id", required = true) @Valid @RequestParam(value = "id", required = true) Long id);
}
