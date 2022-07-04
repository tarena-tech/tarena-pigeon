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
import com.tarena.mnmp.template.SmsTemplate;
import com.tarena.mnmp.template.SmsTemplatePage;
import com.tarena.mnmp.template.WecomTemplate;
import com.tarena.mnmp.template.WecomTemplateData;
import com.tarena.mnmp.template.WecomTemplatePage;
import com.tarena.mnmp.template.WecomTemplatePageQuery;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TemplateController implements TemplateApi {
    @Override public String addSmsTemplate(SmsTemplate smsTemplate) {
        return null;
    }

    @Override public void closeSmsTemplate(Long id) {

    }

    @Override public void openSmsTemplate(Long id) {

    }

    @Override
    public SmsTemplatePage queryListByPage(String keyword, String appCode, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override public List<SmsTemplate> queryListByParam(String keyword, String appCode, Integer noticeType,
        Integer templateType) {
        return null;
    }

    @Override public SmsTemplate querySmsTemplateDetail(Long id) {
        return null;
    }

    @Override public String updateSmsTemplate(SmsTemplate templateSms) {
        return null;
    }

    @Override public void doAuditSmsTemplate(Long id, Integer auditStatus, String auditResult) {

    }

    @Override public String addWecomTemplate(WecomTemplateData wecomTemplateData) {
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

    @Override public String updateWecomTemplate(WecomTemplateData wecomTemplateData) {
        return null;
    }

    @Override public void updateWecomTemplateAuditStatus(Long id, Integer auditStatus, String auditResult) {

    }

    @Override public void updateWecomTemplateUseStatus(Long id, Integer useStatus) {

    }
}
