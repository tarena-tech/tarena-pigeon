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
import com.tarena.mnmp.domain.param.TemplateQuery;
import com.tarena.mnmp.domain.provider.ProviderService;
import com.tarena.mnmp.enums.AuditStatus;
import com.tarena.mnmp.enums.Deleted;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.enums.Role;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.LoginToken;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    @Resource
    private SmsTemplateDao smsTemplateDao;

    @Resource
    private AppService appService;

    @Resource
    private ProviderService providerService;

    public void save(SmsTemplateParam templateParam, LoginToken token) throws BusinessException {

        SmsTemplateDO template = new SmsTemplateDO();
        BeanUtils.copyProperties(templateParam, template);

        checkOnlyOne(templateParam);

        AppDO app = appService.checkStatus(template.getAppId());
        template.setAppCode(app.getCode());

        Date now = new Date();
        if (null == template.getId()) {
            template.setCreateUserId(token.getId());
            template.setDeleted(Deleted.NO.getVal());
            template.setEnabled(Enabled.YES.getVal());
            template.setAuditStatus(AuditStatus.WAITING.getStatus());
            template.setCreateTime(now);
            template.setUpdateTime(now);
            smsTemplateDao.save(template);
            return;
        }

        SmsTemplateDO detail = querySmsTemplateDetail(template.getId());

        if (!Role.executable(token, detail.getCreateUserId())) {
            throw new BusinessException("100", "无权限");
        }


        template.noChangeParam();
        smsTemplateDao.modify(template);
    }

    private void checkOnlyOne(SmsTemplateParam template) throws BusinessException {
        TemplateQuery query = new TemplateQuery();
        query.setExcludeId(template.getId());
        query.setTemplateCode(template.getCode());
        Long count = queryCount(query);
        if (count > 0) {
            throw new BusinessException("100", "[" + template.getCode() + "]编码已存在");
        }
    }

    @Deprecated
    public void closeSmsTemplate(Long id) {
        SmsTemplateDO sms = new SmsTemplateDO();
        sms.setId(id);
        sms.setEnabled(Enabled.NO.getVal());
        smsTemplateDao.modify(sms);
    }

    @Deprecated
    public void openSmsTemplate(Long id) {
        SmsTemplateDO sms = new SmsTemplateDO();
        sms.setId(id);
        sms.setEnabled(Enabled.YES.getVal());
        smsTemplateDao.modify(sms);
    }

    public List<SmsTemplateDO> queryList(TemplateQuery query) {
        List<SmsTemplateDO> sources = smsTemplateDao.queryTemplates(query);
        if (null != query.getAppendId()) {
            boolean append = sources.stream().noneMatch(source -> source.getId().equals(query.getAppendId()));
            SmsTemplateDO smsTemplate;
            if (append && null != (smsTemplate = querySmsTemplateDetail(query.getAppendId()))) {
                sources.add(smsTemplate);
            }
        }
        return sources;
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

    public void doAuditSmsTemplate(SmsTemplateAuditParam param) throws BusinessException {
        if (Objects.equals(AuditStatus.PASS.getStatus(), param.getAuditStatus())) {
            providerService.checkStatus(param.getProviderId());
        }

        SmsTemplateDO bo = new SmsTemplateDO();
        bo.setId(param.getId());
        bo.setAuditStatus(param.getAuditStatus());
        bo.setAuditResult(param.getAuditResult());
        bo.setProviderId(param.getProviderId());
        bo.setContent(param.getContent());
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

        String name = smsTemplate.getName();
        if (!Objects.equals(AuditStatus.PASS.getStatus(), smsTemplate.getAuditStatus())) {
            throw new BusinessException("100", "[" + name + "]" + "短信模板未审核");
        }

        if (!Objects.equals(Enabled.YES.getVal(), smsTemplate.getEnabled())) {
            throw new BusinessException("100",  "[" + name + "]" + "短信模板未启用");
        }

        return smsTemplate;
    }

}
