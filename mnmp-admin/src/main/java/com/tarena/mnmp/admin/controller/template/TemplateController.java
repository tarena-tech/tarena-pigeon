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
import com.tarena.mnmp.admin.codegen.api.template.TemplateView;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.SmsTemplateDO;
import com.tarena.mnmp.domain.template.SmsTemplateParam;
import com.tarena.mnmp.domain.template.TemplateQuery;
import com.tarena.mnmp.domain.template.TemplateService;
import com.tarena.mnmp.protocol.Result;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateController implements TemplateApi {

    @Resource
    private TemplateService templateService;

    @Override public Result<String> addSmsTemplate(SmsTemplateParam param) {
        SmsTemplateDO sms = new SmsTemplateDO();
        BeanUtils.copyProperties(param, sms);
        String str = templateService.addSmsTemplate(sms);
        return new Result<>(str);
    }

    @Override public void closeSmsTemplate(Long id) {
        templateService.closeSmsTemplate(id);
    }

    @Override public void openSmsTemplate(Long id) {
        templateService.openSmsTemplate(id);
    }

    @Override
    public PagerResult<TemplateView> queryListByPage(TemplateQuery templateQuery) {
        List<SmsTemplateDO> dos = templateService.queryListPage(templateQuery);
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

    @Override public List<TemplateView> queryListByParam(String keyword, String appCode, Integer noticeType,
        Integer templateType) {
        List<SmsTemplateDO> dos = templateService.queryListByParam(keyword, appCode, noticeType, templateType);
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

    @Override public void doAuditSmsTemplate(Long id, Integer auditStatus, String auditResult) {
        if (null == auditStatus || auditStatus != 1 && auditStatus != -1) {
            return;
        }
        templateService.doAuditSmsTemplate(id, auditStatus, auditResult);
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
