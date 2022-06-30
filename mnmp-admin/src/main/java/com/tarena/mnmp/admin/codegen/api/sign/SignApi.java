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

package com.tarena.mnmp.admin.codegen.api.sign;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tarena.mnmp.sign.Sign;
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
    value = "签名管理"
)
@RequestMapping("/sign")
public interface SignApi {
    @ApiOperationSupport(order = 3001)
    @ApiOperation(
        value = "新增签名",
        nickname = "addSign",
        notes = "",
        response = String.class
    )
    @PostMapping(
        value = {"/add"},
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    String addSign(@ApiParam(value = "新增签名", required = true) @Valid @RequestBody Sign sign);

    @ApiOperationSupport(order = 3002)
    @ApiOperation(
        value = "关闭签名",
        nickname = "closeSign",
        notes = ""
    )
    @PostMapping({"/close"})
    void closeSign(
        @NotNull @ApiParam(value = "要关闭的签名", required = true) @Valid @RequestParam(value = "id", required = true) Long id);

    @ApiOperationSupport(order = 3003)
    @ApiOperation(
        value = "编辑签名",
        nickname = "editSign",
        notes = "",
        response = String.class
    )
    @PostMapping(
        value = {"/edit"},
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    String editSign(@ApiParam(value = "编辑签名", required = true) @Valid @RequestBody Sign sign);

    @ApiOperationSupport(order = 3004)
    @ApiOperation(
        value = "开启签名",
        nickname = "openSign",
        notes = "",
        tags = {"Sign"}
    )
    @PostMapping({"/open"})
    void openSign(
        @NotNull @ApiParam(value = "要开启的签名id", required = true) @Valid @RequestParam(value = "id", required = true) Long id);

    @ApiOperationSupport(order = 3005)
    @ApiOperation(
        value = "查看签名详情",
        nickname = "querySignDetail",
        notes = "",
        response = Sign.class
    )
    @GetMapping(
        value = {"/queryDetail"},
        produces = {"application/json"}
    )
    Sign querySignDetail(
        @NotNull @ApiParam(value = "签名id", required = true) @Valid @RequestParam(value = "id", required = true) Long id);

    @ApiOperationSupport(order = 3006)
    @ApiOperation(
        value = "查询签名列表",
        nickname = "querySignList",
        notes = "",
        response = Sign.class,
        responseContainer = "List"
    )
    @GetMapping(
        value = {"/queryList"},
        produces = {"application/json"}
    )
    List<Sign> querySignList(
        @ApiParam("应用code") @Valid @RequestParam(value = "appCode", required = false) String appCode,
        @ApiParam("状态 0:未开启 1:开启") @Valid @RequestParam(value = "status", required = false) Integer status,
        @ApiParam("签名名称") @Valid @RequestParam(value = "name", required = false) String name);
}
