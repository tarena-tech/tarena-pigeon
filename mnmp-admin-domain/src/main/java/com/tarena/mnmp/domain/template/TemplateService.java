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
package com.tarena.mnmp.domain.template;


import com.tarena.mnmp.domain.AppDO;
import com.tarena.mnmp.domain.SmsTemplateDO;
import com.tarena.mnmp.domain.app.AppService;
import com.tarena.mnmp.domain.provider.ProviderService;
import com.tarena.mnmp.enums.AuditStatus;
import com.tarena.mnmp.enums.Deleted;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    @Resource
    private SmsTemplateDao smsTemplateDao;

    @Resource
    private AppService appService;

    @Resource
    private ProviderService providerService;

    public String save(SmsTemplateDO template) throws BusinessException {

        AppDO app = appService.checkStatus(template.getAppId());
        template.setAppCode(app.getCode());
        providerService.checkStatus(template.getProviderId());

        Date now = new Date();
        if (null == template.getId()) {
            template.setDeleted(Deleted.NO.getVal());
            template.setEnabled(Enabled.YES.getVal());
            template.setAuditStatus(0);
            template.setCreateTime(now);
            template.setUpdateTime(now);
            smsTemplateDao.save(template);
        } else {
            template.setCreateTime(null);
            template.setEnabled(null);
            template.setAuditStatus(null);
            template.setAuditResult(null);
            template.setUseCount(null);
            smsTemplateDao.modify(template);
        }
        return "ok";
    }

    public void closeSmsTemplate(Long id) {
        SmsTemplateDO sms = new SmsTemplateDO();
        sms.setId(id);
        sms.setEnabled(Enabled.NO.getVal());
        smsTemplateDao.modify(sms);
    }

    public void openSmsTemplate(Long id) {
        SmsTemplateDO sms = new SmsTemplateDO();
        sms.setId(id);
        sms.setEnabled(Enabled.YES.getVal());
        smsTemplateDao.modify(sms);
    }

    public List<SmsTemplateDO> queryList(TemplateQuery query) {
        return smsTemplateDao.queryTemplates(query);
    }

    public Long queryCount(TemplateQuery query) {
        Long count = smsTemplateDao.queryCount(query);
        return Optional.ofNullable(count).orElse(0L);
    }

    public SmsTemplateDO querySmsTemplateDetail(Long id) {
        return smsTemplateDao.findById(id);
    }

    public String updateSmsTemplate(SmsTemplateDO sms) {
        smsTemplateDao.modify(sms);
        return "ok";
    }

    public void doAuditSmsTemplate(Long id, Integer status, String auditResult) {
        SmsTemplateDO bo = new SmsTemplateDO();
        bo.setId(id);
        bo.setAuditStatus(status);
        bo.setAuditResult(auditResult);
        smsTemplateDao.modify(bo);
    }

    public void changeEnableByAppId(Long id, Integer enable) {
        smsTemplateDao.changeEnableByAppId(id, enable);
    }

    public void changeEnableByProviderId(Long providerId, Integer enabled) {
        smsTemplateDao.changeEnableByProviderId(providerId, enabled);
    }

    public SmsTemplateDO checkStatus(Long id) throws BusinessException {
        SmsTemplateDO smsTemplate = querySmsTemplateDetail(id);
        if (null == smsTemplate) {
            throw new BusinessException("100", "短信模板不存在");
        }

        if (!Objects.equals(AuditStatus.PASS.getStatus(), smsTemplate.getAuditStatus())) {
            throw new BusinessException("100", "短信模板未审核");
        }

        if (!Objects.equals(Enabled.YES.getVal(), smsTemplate.getEnabled())) {
            throw new BusinessException("100", "短信模板未启用");
        }

        return smsTemplate;
    }

}
