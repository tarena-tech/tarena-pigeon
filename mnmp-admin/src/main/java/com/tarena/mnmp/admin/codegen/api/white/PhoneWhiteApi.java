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

package com.tarena.mnmp.admin.codegen.api.white;

import com.github.xiaoymin.knife4j.annotations.Ignore;
import com.tarena.mnmp.admin.annotation.User;
import com.tarena.mnmp.admin.controller.white.PhoneWhiteView;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.param.PhoneWhiteQueryParam;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.LoginToken;
import com.tarena.mnmp.protocol.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Validated
@Api(
    value = "PhoneWhite",
    tags = "手机号白名单管理"
)
@RequestMapping("/white/phone")
public interface PhoneWhiteApi {


    @ApiOperation(value = "获取excel模版文件")
    @GetMapping("excel")
    @PreAuthorize("hasAnyRole('admin','root')")
    void getExcel(String path, @Ignore HttpServletResponse response) throws BusinessException, IOException;


    @ApiOperation(value = "查询白名单")
    @GetMapping("query/page")
    @PreAuthorize("hasAnyRole('admin','root')")
    Result<PagerResult<PhoneWhiteView>> queryPage(PhoneWhiteQueryParam param, @User LoginToken token);

    @ApiOperation(value = "上传文件")
    @PostMapping("save/file")
    @PreAuthorize("hasAnyRole('admin','root')")
    Result<String> saveByFile(@RequestParam("file") MultipartFile file, @User LoginToken token, HttpServletResponse response) throws IOException, BusinessException;

    @ApiOperation(value = "删除")
    @PostMapping("del")
    @PreAuthorize("hasAnyRole('admin','root')")
    Result<Void> dels(Long id, String appCode, @User LoginToken token) throws BusinessException;




}
