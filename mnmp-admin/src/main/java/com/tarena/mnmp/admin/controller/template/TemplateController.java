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
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.SmsTemplateDO;
import com.tarena.mnmp.domain.app.AppService;
import com.tarena.mnmp.domain.provider.ProviderService;
import com.tarena.mnmp.domain.task.TaskQuery;
import com.tarena.mnmp.domain.task.TaskService;
import com.tarena.mnmp.domain.template.SmsTemplateAuditParam;
import com.tarena.mnmp.domain.template.SmsTemplateParam;
import com.tarena.mnmp.domain.param.TemplateQuery;
import com.tarena.mnmp.domain.template.TemplateService;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.LoginToken;
import com.tarena.mnmp.enums.Role;
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
    private AppService appService;

    @Resource
    private ProviderService providerService;

    @Resource
    private TaskService taskService;

    @Override public void save(SmsTemplateParam param, LoginToken token) throws BusinessException {
        templateService.save(param, token);
    }

    @Override public void changeEnableStatus(Long id, LoginToken token) throws BusinessException {
        SmsTemplateDO smstempalte = templateService.querySmsTemplateDetail(id);
        if (null == smstempalte) {
            throw new BusinessException("100", "模板不存在");
        }

        if (!Role.manager(token.getRole()) && !Objects.equals(smstempalte.getCreateUserId(), token.getId())) {
            throw new BusinessException("100", "无权限");
        }

        // 如果之前禁用 改为 启用， 则需要校验管理数据
        if (Objects.equals(Enabled.NO.getVal(), smstempalte.getEnabled())) {
            appService.checkStatus(smstempalte.getAppId());
            providerService.checkStatus(smstempalte.getProviderId());
        }

        SmsTemplateDO up = new SmsTemplateDO();
        up.setId(id);
        up.setEnabled(Enabled.reverse(smstempalte.getEnabled()).getVal());
        templateService.updateSmsTemplate(up);

        if (Objects.equals(Enabled.NO.getVal(), up.getEnabled())) {
            TaskQuery query = new TaskQuery();
            query.setTemplateId(id);
            taskService.endTaskStatusByTargetId(query);
        }

    }

    @Override
    public PagerResult<TemplateView> queryListByPage(TemplateQuery templateQuery, LoginToken token) {
        if (!Role.manager(token.getRole())) {
            templateQuery.setCreateUserId(token.getId());
        }
        List<SmsTemplateDO> sources = templateService.queryList(templateQuery);
        PagerResult<TemplateView> page = new PagerResult<>(templateQuery.getPageSize(), templateQuery.getCurrentPageIndex());
        page.setList(TemplateView.convert(sources));
        page.setRecordCount(templateService.queryCount(templateQuery));
        return page;
    }

    @Override public List<TemplateView> queryListByParam(TemplateQuery templateQuery, LoginToken token) {
        if (!Role.manager(token.getRole())) {
            templateQuery.setCreateUserId(token.getId());
        }
        List<SmsTemplateDO> sources = templateService.queryList(templateQuery);
        return TemplateView.convert(sources);
    }

    @Override public TemplateView querySmsTemplateDetail(Long id) {

        SmsTemplateDO bo = templateService.querySmsTemplateDetail(id);
        TemplateView view = new TemplateView();
        BeanUtils.copyProperties(bo, view);
        return view;
    }


    @Override public void doAuditSmsTemplate(SmsTemplateAuditParam param) {
        templateService.doAuditSmsTemplate(param);
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
