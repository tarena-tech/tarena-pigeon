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
import com.github.xiaoymin.knife4j.annotations.Ignore;
import com.tarena.mnmp.admin.annotation.User;
import com.tarena.mnmp.admin.controller.sign.SignView;
import com.tarena.mnmp.admin.param.AuditParam;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.sign.SignSaveParam;
import com.tarena.mnmp.domain.sign.SignQuery;
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
import springfox.documentation.annotations.ApiIgnore;

@Validated
@Api(
    value = "签名管理",
    tags = "签名管理"
)
@RequestMapping("/sign")
public interface SignApi {
    @ApiOperationSupport(order = 3001)
    @ApiOperation(
        value = "新增签名"
    )
    @PostMapping(
        value = {"/save"}
    )
    @PreAuthorize("hasAnyRole('admin','root', 'user')")
    void save(@ApiParam(value = "新增签名", required = true) @Valid @RequestBody SignSaveParam signParam,
        @ApiIgnore @User LoginToken token) throws BusinessException;


    @ApiOperation("启用/禁用")
    @PostMapping("change/enable/status")
    @PreAuthorize("hasAnyRole('admin','root', 'user')")
    void changeEnableStatus(Long id, @Ignore @User LoginToken token) throws BusinessException;

    @ApiOperationSupport(order = 3005)
    @ApiOperation(
        value = "查看签名详情"
    )
    @GetMapping("/queryDetail")
    @ApiParam(value = "签名id", required = true)
    @PreAuthorize("hasAnyRole('admin','root','user')")
    SignView querySignDetail(
        @NotNull  @Valid @RequestParam(value = "id", required = true) Long id);

    @ApiOperationSupport(order = 3006)
    @ApiOperation(
        value = "查询签名列表"
    )
    @GetMapping(
        value = {"/queryPage"}
    )
    @PreAuthorize("hasAnyRole('admin','root','user')")
    PagerResult<SignView> queryPage(@ApiParam(value = "签名查询入参") SignQuery signQuery,
        @ApiIgnore @User LoginToken token);

    @GetMapping(
        value = {"/queryList"}
    )
    @PreAuthorize("hasAnyRole('admin','root','user')")
    List<SignView> queryList(@ApiParam(value = "签名查询入参") SignQuery signQuery);

    /**
     * 审核签名
     */
    @PostMapping("audit")
    @PreAuthorize("hasAnyRole('admin','root')")
    void auditSign(@RequestBody AuditParam param);
}
