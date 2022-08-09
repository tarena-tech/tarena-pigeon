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
import com.tarena.mnmp.protocol.Result;
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
    Result<String> save(@ApiParam(value = "新增短信模板", required = true) @Valid @RequestBody SmsTemplateParam smsTemplate) throws BusinessException;

    @ApiOperationSupport(order = 4002)
    @ApiOperation(
        value = "关闭使用短信模板"
    )
    @PostMapping({"/sms/close"})
    @Deprecated
    void closeSmsTemplate(@Validated Long id);

    @ApiOperationSupport(order = 4003)
    @ApiOperation(
        value = "开启使用短信模板"
    )
    @PostMapping({"/sms/open"})
    @Deprecated
    void openSmsTemplate(@Validated @RequestBody Long id);

    @PostMapping("sms/change/enable/status")
    void changeEnableStatus(Long id) throws BusinessException;

    @ApiOperationSupport(order = 4004)
    @ApiOperation(
        value = "查询短信模板信息（分页）"
    )
    @GetMapping(
        value = {"/sms/query/page"},
        produces = {"application/json"}
    )
    PagerResult<TemplateView> queryListByPage(TemplateQuery templateQuery);

//    @ApiOperationSupport(order = 4005)
    @ApiOperation(
        value = "查询短信模板信息"
    )
    @GetMapping(
        value = {"/sms/query/list"},
        produces = {"application/json"}
    )
    List<TemplateView> queryListByParam(TemplateQuery templateQuery);

    @ApiOperationSupport(order = 4006)
    @ApiOperation(
        value = "查看短信模板详情"
    )
    @GetMapping(
        value = {"/sms/queryDetail"},
        produces = {"application/json"}
    )
    TemplateView querySmsTemplateDetail(
        @NotNull @ApiParam(value = "模板id", required = true) @Valid @RequestParam(value = "id", required = true) Long id);

    @ApiOperationSupport(order = 4007)
    @ApiOperation(
        value = "修改短信模板"
    )
    @PostMapping(
        value = {"/sms/update"},
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    Result<String> updateSmsTemplate(
        @ApiParam(value = "修改短信模板(未通过审核)", required = true) @Valid @RequestBody SmsTemplateParam templateSms);

    @ApiOperationSupport(order = 4008)
    @ApiOperation(
        value = "短信模板审核"
    )
    @PostMapping({"/sms/doAudit"})
    void doAuditSmsTemplate(@RequestBody AuditParam param);

//    @ApiOperationSupport(order = 4101)
//    @ApiOperation(
//        value = "新增企微模板",
//        nickname = "addWecomTemplate",
//        notes = "",
//        response = String.class
//    )
//    @PostMapping(
//        value = {"/wecom"},
//        produces = {"application/json"}
//    )
//    String addWecomTemplate(
//        @ApiParam(value = "新增企微模板", required = true) @Valid @RequestBody WecomTemplateVO wecomTemplateVO);
//
//    @ApiOperationSupport(order = 4102)
//    @ApiOperation(
//        value = "查询企微模板信息（分页）",
//        nickname = "queryWecomListByPage",
//        notes = "",
//        response = WecomTemplatePage.class
//    )
//    @PostMapping(
//        value = {"/wecom/page"},
//        produces = {"application/json"},
//        consumes = {"application/json"}
//    )
//    WecomTemplatePage queryWecomListByPage(
//        @ApiParam(value = "模版分页查询参数", required = true) @Valid @RequestBody WecomTemplatePageQuery wecomTemplatePageQuery);
//
//    @ApiOperationSupport(order = 4103)
//    @ApiOperation(
//        value = "查看企微模板详情",
//        nickname = "queryWecomTemplateDetail",
//        notes = "",
//        response = WecomTemplate.class
//    )
//    @GetMapping(
//        value = {"/wecom/{templateId}"},
//        produces = {"application/json"}
//    )
//    WecomTemplate queryWecomTemplateDetail(
//        @ApiParam(value = "模板id", required = true) @PathVariable("templateId") Long templateId);
//
//    @ApiOperationSupport(order = 4104)
//    @ApiOperation(
//        value = "查询企微模板信息",
//        nickname = "queryWecomTemplatesByParam",
//        notes = "",
//        response = WecomTemplate.class,
//        responseContainer = "List"
//    )
//    @GetMapping(
//        value = {"/wecoms"},
//        produces = {"application/json"}
//    )
//    List<WecomTemplate> queryWecomTemplatesByParam(
//        @ApiParam("根据模板名称/模板code模糊查询") @Valid @RequestParam(value = "keyword", required = false) String keyword,
//        @ApiParam("根据应用id查询") @Valid @RequestParam(value = "appId", required = false) Long appId,
//        @ApiParam("根据消息类型查询  3:wecom") @Valid @RequestParam(value = "noticeType", required = false) Integer noticeType,
//        @ApiParam("根据消息子类型查询 1:文本 2:图片 3:视频 4:文件 5:文本卡片") @Valid @RequestParam(value = "templateType", required = false) Integer templateType);
//
//    @ApiOperationSupport(order = 4105)
//    @ApiOperation(
//        value = "修改企微模板",
//        nickname = "updateWecomTemplate",
//        notes = "",
//        response = String.class
//    )
//    @PutMapping(
//        value = {"/wecom"},
//        produces = {"application/json"}
//    )
//    String updateWecomTemplate(
//        @ApiParam(value = "修改企微模板(未通过审核)", required = true) @Valid @RequestBody WecomTemplateVO wecomTemplateVO);
//
//    @ApiOperationSupport(order = 4106)
//    @ApiOperation(
//        value = "企微模板审核",
//        nickname = "updateWecomTemplateAuditStatus",
//        notes = ""
//    )
//    @PutMapping({"/wecom/{id}/audit/{auditStatus}"})
//    void updateWecomTemplateAuditStatus(@ApiParam(value = "企微模版id", required = true) @PathVariable("id") Long id,
//        @ApiParam(value = "审核状态 -1未通过 1通过", required = true) @PathVariable("auditStatus") Integer auditStatus,
//        @ApiParam("审核意见") @Valid @RequestParam(value = "auditResult", required = false) String auditResult);
//
//    @ApiOperationSupport(order = 4107)
//    @ApiOperation(
//        value = "企微模版启用停用",
//        nickname = "updateWecomTemplateUseStatus",
//        notes = ""
//    )
//    @PutMapping({"/wecom/{id}/useage/{useStatus}"})
//    void updateWecomTemplateUseStatus(@ApiParam(value = "模板id", required = true) @PathVariable("id") Long id,
//        @ApiParam(value = "使用状态 1启用 0停用", required = true) @PathVariable("useStatus") Integer useStatus);
}
