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
package com.tarena.mnmp.admin.codegen.api.record;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tarena.mnmp.admin.annotation.User;
import com.tarena.mnmp.admin.controller.record.SmsTargetRecordView;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.param.SmsTargetRecordParam;
import com.tarena.mnmp.protocol.LoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Api(
    value = "TaskTargetRecord",
    tags = "任务发送记录管理"
)
@RequestMapping("/record/target/sms")
public interface SmsTargetRecordApi {


    @ApiOperationSupport(order = 6000)
    @ApiOperation(
        value = "查询任务列表信息（分页）"
    )
    @PostMapping(value = {"/query/page"})
    @PreAuthorize("hasAnyRole('admin','root','user')")
    PagerResult<SmsTargetRecordView> queryPage(@Valid @RequestBody SmsTargetRecordParam data, @User LoginToken token);
}
