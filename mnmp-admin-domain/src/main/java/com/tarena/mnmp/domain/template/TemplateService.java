package com.tarena.mnmp.domain.template;

import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.SmsTemplateDO;
import com.tarena.mnmp.enums.Deleted;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    @Resource
    private SmsTemplateDao smsTemplateDao;

    public String addSmsTemplate(SmsTemplateDO template) {


        Date now = new Date();
        template.setDeleted(Deleted.NO.getVal());
        template.setEnabled(Enabled.YES.getVal());
        template.setAuditStatus(0);
        template.setCreateTime(now);
        template.setUpdateTime(now);
        smsTemplateDao.save(template);
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

    public List<SmsTemplateDO> queryListPage(TemplateQuery query) {
        return smsTemplateDao.queryTemplates(query);
    }

    public Long queryCount(TemplateQuery query) {
        Long count = smsTemplateDao.queryCount(query);
        return Optional.ofNullable(count).orElse(0L);
    }

    public List<SmsTemplateDO> queryListByParam(String keyword, String code, Integer type, Integer templateType) {
        return smsTemplateDao.search(keyword, code, type, templateType);
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
}
