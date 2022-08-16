package com.tarena.test.mnmp.admin.service.template;
import com.tarena.mnmp.domain.template.SmsTemplateParam;
import com.tarena.mnmp.domain.template.TemplateQuery;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.test.mnmp.admin.sql.app.AppSqlScript;
import java.util.Date;

import com.tarena.mnmp.admin.AdminApplication;
import com.tarena.mnmp.domain.SmsTemplateDO;
import com.tarena.mnmp.domain.template.TemplateService;
import java.util.List;
import javax.annotation.Resource;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(classes = {AdminApplication.class})
@Ignore
public class TemplateServiceTest {


    Logger logger = LoggerFactory.getLogger(TemplateServiceTest.class);
    @Resource
    private TemplateService templateService;



    @Test
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void addSmsTemplate() throws BusinessException {
        for (int i = 0 ; i < 100; i++) {
            SmsTemplateParam sms = new SmsTemplateParam();
            sms.setCode("xx-ss-" + i);
            sms.setAppId(1L + i);
            sms.setName("测试模板00" + i);
            sms.setTemplateType(1);
            sms.setContent("模板内容" + i);
            sms.setRemark("备注" + i);
            sms.setAuditStatus(1);
            sms.setAuditResult("结果内容" + i);
            sms.setEnabled(1);
            sms.setNoticeType(1);
            sms.setAppCode("2qqq");
            templateService.save(sms);
        }


    }

    @Test
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void closeSmsTemplate () {
        templateService.closeSmsTemplate(2L);
    }

    @Test
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void openSmsTemplate() {
        templateService.openSmsTemplate(2L);
    }

    @Test
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void queryListPage () {
        TemplateQuery query = new TemplateQuery();
        query.setAppCode("tmtc");
        query.setAuditStatus(1);
        query.setEnabled(1);
        query.setTemplateCode("");
        query.setTemplateName("");
        query.setPageSize(10);
        query.setCurrentPageIndex(1);

        List<SmsTemplateDO> dos = templateService.queryList(query);
        Long count = templateService.queryCount(query);
        logger.info("page:{}", dos.size());

    }

    @Test
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void queryListByParam() {
        List<SmsTemplateDO> dos = templateService.queryList(null);
        logger.info("size:{}", dos.size());
    }

    @Test
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void querySmsTemplateDetail () {
        SmsTemplateDO smsTemplateDO = templateService.querySmsTemplateDetail(1L);
        logger.info("detail:{}", smsTemplateDO.toString());
    }

    @Test
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void updateSmsTemplate () {
        SmsTemplateDO sms = new SmsTemplateDO();
        sms.setId(1L);
        sms.setRemark("update test");
        templateService.updateSmsTemplate(sms);
    }

    @Test
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void doAuditSmsTemplate() {
        templateService.doAuditSmsTemplate(null);
    }


}
