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

package com.tarena.mnmp.admin.codegen.api.template;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tarena.mnmp.admin.controller.template.TemplateView;
import com.tarena.mnmp.admin.param.AuditParam;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.template.SmsTemplateParam;
import com.tarena.mnmp.domain.template.TemplateQuery;
import com.tarena.mnmp.protocol.BusinessException;
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

@Validated
@Api(
    value = "模板管理",
    tags = "模板管理"
)
@RequestMapping("template")
public interface TemplateApi {
    @ApiOperationSupport(order = 4001)
    @ApiOperation(
        value = "新增短信模板"
    )
    @PostMapping(
        value = {"/sms/save"}
    )
    @PreAuthorize("hasAnyRole('admin','root','user')")
    void save(@ApiParam(value = "新增短信模板", required = true) @Valid @RequestBody SmsTemplateParam smsTemplate) throws BusinessException;



    @PostMapping("sms/change/enable/status")
    @ApiOperation("变更短信模板启用状态")
    @PreAuthorize("hasAnyRole('admin','root','user')")
    void changeEnableStatus(Long id) throws BusinessException;

    @ApiOperationSupport(order = 4004)
    @ApiOperation(value = "查询短信模板信息（分页）")
    @GetMapping("/sms/query/page")
    @PreAuthorize("hasAnyRole('admin','root','user')")
    PagerResult<TemplateView> queryListByPage(TemplateQuery templateQuery);

    @ApiOperation(value = "查询短信模板信息")
    @GetMapping("/sms/query/list")
    @PreAuthorize("hasAnyRole('admin','root','user')")
    List<TemplateView> queryListByParam(TemplateQuery templateQuery);

    @ApiOperationSupport(order = 4006)
    @ApiOperation(value = "查看短信模板详情")
    @ApiParam(value = "模板id", required = true)
    @GetMapping("/sms/queryDetail")
    @PreAuthorize("hasAnyRole('admin','root','user')")
    TemplateView querySmsTemplateDetail(@NotNull @Valid @RequestParam(value = "id", required = true) Long id);


    @ApiOperationSupport(order = 4008)
    @ApiOperation(
        value = "短信模板审核"
    )
    @PostMapping({"/sms/doAudit"})
    @PreAuthorize("hasAnyRole('admin','root')")
    void doAuditSmsTemplate(@RequestBody AuditParam param);
}
