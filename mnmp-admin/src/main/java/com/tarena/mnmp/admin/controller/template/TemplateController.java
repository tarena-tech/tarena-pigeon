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

package com.tarena.mnmp.admin.controller.template;

import com.tarena.mnmp.admin.codegen.api.template.TemplateApi;
import com.tarena.mnmp.admin.param.AuditParam;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.SmsTemplateDO;
import com.tarena.mnmp.domain.task.TaskQuery;
import com.tarena.mnmp.domain.task.TaskService;
import com.tarena.mnmp.domain.template.SmsTemplateParam;
import com.tarena.mnmp.domain.template.TemplateQuery;
import com.tarena.mnmp.domain.template.TemplateService;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateController implements TemplateApi {

    @Resource
    private TemplateService templateService;

    @Resource
    private TaskService taskService;

    @Override public Result<String> save(SmsTemplateParam param) throws BusinessException {
        SmsTemplateDO sms = new SmsTemplateDO();
        BeanUtils.copyProperties(param, sms);
        String str = templateService.save(sms);
        return new Result<>(str);
    }

    @Override public void closeSmsTemplate(Long id) {
        templateService.closeSmsTemplate(id);
    }

    @Override public void openSmsTemplate(Long id) {
        templateService.openSmsTemplate(id);
    }

    @Override public void changeEnableStatus(Long id) throws BusinessException {
        SmsTemplateDO smsTemplateDO = templateService.querySmsTemplateDetail(id);
        if (null == smsTemplateDO) {
            throw new BusinessException("100", "模板不存在");
        }

        SmsTemplateDO up = new SmsTemplateDO();
        up.setId(id);
        up.setEnabled(Enabled.reverse(smsTemplateDO.getEnabled()).getVal());
        templateService.updateSmsTemplate(up);

        if (Objects.equals(Enabled.NO.getVal(), up.getEnabled())) {
            TaskQuery query = new TaskQuery();
            query.setTemplateId(id);
            taskService.endTaskStatusByTargetId(query);
        }

    }

    @Override
    public PagerResult<TemplateView> queryListByPage(TemplateQuery templateQuery) {
        List<SmsTemplateDO> dos = templateService.queryList(templateQuery);
        PagerResult<TemplateView> page = new PagerResult<>(templateQuery.getPageSize(), templateQuery.getCurrentPageIndex());

        List<TemplateView> list = new ArrayList<>();
        dos.forEach(li -> {
            TemplateView view = new TemplateView();
            BeanUtils.copyProperties(li, view);
            list.add(view);
        });

        page.setList(list);
        page.setRecordCount(templateService.queryCount(templateQuery));
        return page;
    }

    @Override public List<TemplateView> queryListByParam(TemplateQuery templateQuery) {
        List<SmsTemplateDO> dos = templateService.queryList(templateQuery);
        List<TemplateView> list = new ArrayList<>();
        dos.forEach(l -> {
            TemplateView view = new TemplateView();
            BeanUtils.copyProperties(l, view);
            list.add(view);
        });

        return list;
    }

    @Override public TemplateView querySmsTemplateDetail(Long id) {

        SmsTemplateDO bo = templateService.querySmsTemplateDetail(id);
        TemplateView view = new TemplateView();
        BeanUtils.copyProperties(bo, view);
        return view;
    }

    @Override public Result<String> updateSmsTemplate(SmsTemplateParam templateSms) {
        SmsTemplateDO sms = new SmsTemplateDO();
        BeanUtils.copyProperties(templateSms, sms);
        String str = templateService.updateSmsTemplate(sms);
        return new Result<>(str);
    }

    @Override public void doAuditSmsTemplate(AuditParam param) {
        templateService.doAuditSmsTemplate(param.getId(), param.getAuditStatus(), param.getAuditResult());
    }
//
//    @Override public String addWecomTemplate(WecomTemplateVO wecomTemplateVO) {
//        return null;
//    }
//
//    @Override public WecomTemplatePage queryWecomListByPage(WecomTemplatePageQuery wecomTemplatePageQuery) {
//        return null;
//    }
//
//    @Override public WecomTemplate queryWecomTemplateDetail(Long templateId) {
//        return null;
//    }
//
//    @Override public List<WecomTemplate> queryWecomTemplatesByParam(String keyword, Long appId, Integer noticeType,
//        Integer templateType) {
//        return null;
//    }
//
//    @Override public String updateWecomTemplate(WecomTemplateVO wecomTemplateVO) {
//        return null;
//    }
//
//    @Override public void updateWecomTemplateAuditStatus(Long id, Integer auditStatus, String auditResult) {
//
//    }
//
//    @Override public void updateWecomTemplateUseStatus(Long id, Integer useStatus) {
//
//    }
}
