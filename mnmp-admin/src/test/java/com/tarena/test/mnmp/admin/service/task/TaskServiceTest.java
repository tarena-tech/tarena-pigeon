package com.tarena.test.mnmp.admin.service.task;

import com.tarena.mnmp.admin.AdminApplication;
import com.tarena.mnmp.domain.task.TaskService;
import javax.annotation.Resource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {AdminApplication.class})
//@Ignore
public class TaskServiceTest {


    @Resource
    private TaskService taskService;







}
