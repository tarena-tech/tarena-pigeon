package com.tarena.test.mnmp.admin.service.task;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.TaskTargetDO;
import com.tarena.mnmp.domain.task.TaskQuery;
import com.tarena.mnmp.domain.task.TaskTargetService;
import com.tarena.mnmp.enums.AuditStatus;
import com.tarena.mnmp.enums.Deleted;
import com.tarena.mnmp.enums.TaskStatus;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.test.mnmp.admin.service.template.TemplateServiceTest;
import java.util.ArrayList;
import java.util.Date;

import com.tarena.mnmp.admin.AdminApplication;
import com.tarena.mnmp.domain.TaskDO;
import com.tarena.mnmp.domain.task.TaskDao;
import com.tarena.mnmp.domain.task.TaskService;
import com.tarena.test.mnmp.admin.sql.app.AppSqlScript;
import java.util.List;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(classes = {AdminApplication.class})
//@Ignore
public class TaskServiceTest {

    Logger logger = LoggerFactory.getLogger(TemplateServiceTest.class);

    @Resource
    private TaskService taskService;

    @Resource
    private TaskDao taskDao;

    @Resource
    private TaskTargetService taskTargetService;

    @Test
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
        scripts = {AppSqlScript.TRUNCATE_TABLE},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void doAudit () throws BusinessException {
        taskService.doAudit(1L, 1, "sdfsfsd");
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
    public void addTask () {
        TaskDO task = new TaskDO();
        task.setName("测试任务");
        task.setTaskStatus(TaskStatus.TASK_NO_OPEN.status());
        task.setTargetFileName("todo");
        task.setTaskType(1);
        task.setNoticeType(1);
        task.setTemplateId(1L);
        task.setSignId(1L);
        task.setAppId(1L);
        task.setCycleLevel(1);
        task.setCycleNum(1);
        task.setFirstTriggerTime(new Date());
        task.setTriggerEndTime(new Date());
        task.setNextTriggerTime(new Date());
        task.setTargetType(1);
        task.setTargetFileName("sfs");
        task.setTargetFileUrl("rewrwrw");
        task.setCreator(2);
        task.setCreatorEmail("sdf");
        task.setCreatorName("sfsdf");
        task.setDeptId(1L);
        task.setRemark("weewrew");
        task.setError("wrwer");
        task.setCreateTime(new Date());
        task.setUpdateTime(new Date());
        taskDao.save(task);

        List<TaskTargetDO> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            TaskTargetDO ttask = new TaskTargetDO();
            ttask.setTaskId(task.getId());
            ttask.setDeleted(Deleted.NO.getVal());
            ttask.setParams("323424");
            ttask.setTarget("3242423");
            list.add(ttask);
        }
        taskTargetService.saveBatch(list);
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
    public void action () {
        taskService.action(3L, TaskStatus.TASK_END.status());
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
    public void queryListByPage () {
        TaskQuery query = new TaskQuery();
        query.setName("测试");
        query.setPageSize(10);
        query.setCurrentPageIndex(1);

        PagerResult<TaskDO> result = taskService.queryListByPage(query, null);
        logger.info("size:{}", result.getRecordCount());
    }



}
