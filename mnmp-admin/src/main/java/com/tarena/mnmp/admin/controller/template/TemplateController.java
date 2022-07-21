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
import com.tarena.mnmp.admin.view.template.WecomTemplateVO;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.template.SmsTemplateDO;
import com.tarena.mnmp.domain.template.TemplateQuery;
import com.tarena.mnmp.domain.template.WecomTemplate;
import com.tarena.mnmp.domain.template.WecomTemplatePage;
import com.tarena.mnmp.domain.template.WecomTemplatePageQuery;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateController implements TemplateApi {
    @Override public String addSmsTemplate(SmsTemplateDO smsTemplate) {
        return null;
    }

    @Override public void closeSmsTemplate(Long id) {

    }

    @Override public void openSmsTemplate(Long id) {

    }

    @Override
    public PagerResult<TemplateView> queryListByPage(TemplateQuery templateQuery) {
        return null;
    }

    @Override public List<SmsTemplateDO> queryListByParam(String keyword, String appCode, Integer noticeType,
        Integer templateType) {
        return null;
    }

    @Override public SmsTemplateDO querySmsTemplateDetail(Long id) {
        return null;
    }

    @Override public String updateSmsTemplate(SmsTemplateDO templateSms) {
        return null;
    }

    @Override public void doAuditSmsTemplate(Long id, Integer auditStatus, String auditResult) {

    }

    @Override public String addWecomTemplate(WecomTemplateVO wecomTemplateVO) {
        return null;
    }

    @Override public WecomTemplatePage queryWecomListByPage(WecomTemplatePageQuery wecomTemplatePageQuery) {
        return null;
    }

    @Override public WecomTemplate queryWecomTemplateDetail(Long templateId) {
        return null;
    }

    @Override public List<WecomTemplate> queryWecomTemplatesByParam(String keyword, Long appId, Integer noticeType,
        Integer templateType) {
        return null;
    }

    @Override public String updateWecomTemplate(WecomTemplateVO wecomTemplateVO) {
        return null;
    }

    @Override public void updateWecomTemplateAuditStatus(Long id, Integer auditStatus, String auditResult) {

    }

    @Override public void updateWecomTemplateUseStatus(Long id, Integer useStatus) {

    }
}
