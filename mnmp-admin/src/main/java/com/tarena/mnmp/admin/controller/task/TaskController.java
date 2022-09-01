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

package com.tarena.mnmp.admin.controller.task;

import com.alibaba.excel.EasyExcel;
import com.tarena.mnmp.admin.codegen.api.task.TaskApi;
import com.tarena.mnmp.domain.param.AuditParam;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.admin.utils.ExcelUtils;
import com.tarena.mnmp.domain.AppDO;
import com.tarena.mnmp.domain.SignDO;
import com.tarena.mnmp.domain.SmsTemplateDO;
import com.tarena.mnmp.domain.TaskDO;
import com.tarena.mnmp.domain.app.AppService;
import com.tarena.mnmp.domain.sign.SignService;
import com.tarena.mnmp.domain.task.TargetExcelData;
import com.tarena.mnmp.domain.task.TaskQuery;
import com.tarena.mnmp.domain.task.TaskService;
import com.tarena.mnmp.domain.task.TaskStatistics;
import com.tarena.mnmp.domain.template.TemplateService;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.LoginToken;
import com.tarena.mnmp.protocol.Result;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class TaskController implements TaskApi {

    private static Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @Resource
    private AppService appService;

    @Resource
    private SignService signService;

    @Resource
    private TemplateService templateService;

    @Value("${excel.task.path}")
    private String excelPath;

    private void export(HttpServletResponse response, InputStream is, String fileName) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setHeader("Content-excelname", fileName);
        OutputStream os = response.getOutputStream();
        byte[] buff = new byte[1024];
        int i;
        while ((i = is.read(buff)) != -1) {
            os.write(buff, 0, i);
            os.flush();
        }
    }

    @Override public void getExcel(String path, HttpServletResponse response) throws BusinessException {
        ExcelUtils.getExcel(StringUtils.isEmpty(path) ? null : excelPath + path, response);
    }

    @Override public Result<String> uploadFile(MultipartFile file) throws IOException, BusinessException {
        String path = ExcelUtils.saveLocal(excelPath, file);
        return new Result<>(path);
    }

    @Override public Result<Void> addTask(TaskParam taskParam, HttpServletResponse response, LoginToken token)
        throws IOException, BusinessException {
        TaskDO bo = new TaskDO();
        BeanUtils.copyProperties(taskParam, bo);
        bo.setCreateUserId(token.getId());


        List<TargetExcelData> fial = taskService.addTask(bo, taskParam.getFilePath());
        if (!CollectionUtils.isEmpty(fial)) {
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            String fileName = "非法数据.xlsx";
            response.setHeader("Content-excelname", fileName);
            EasyExcel.write(response.getOutputStream(), TargetExcelData.class).sheet().doWrite(fial);
        }
        return Result.success();
    }

    @Override public void doAudit(AuditParam param) throws BusinessException {
        taskService.doAudit(param.getId(), param.getAuditStatus(), param.getAuditResult());
    }

    @Override public PagerResult<TaskView> queryListByPage(TaskQuery taskQuery, LoginToken token) {
        PagerResult<TaskDO> res = taskService.queryListByPage(taskQuery, token);
        PagerResult<TaskView> result = new PagerResult<>(taskQuery.getPageSize(), taskQuery.getCurrentPageIndex());
        result.setList(TaskView.convert(res.getList()));
        result.setRecordCount(res.getRecordCount());
        return result;
    }

    @Override public TaskView queryTaskDetail(Long id) throws BusinessException {
        TaskDO task = taskService.detailById(id);
        if (null == task) {
            throw new BusinessException("100", "任务不存在");
        }

        TaskView tv = new TaskView();

        AppDO aDo = appService.queryAppDetail(task.getAppId());
        if (null != aDo) {
            tv.setAppName(aDo.getName());
        }

        SignDO sign = signService.querySignDetail(task.getSignId());
        if (null != sign) {
            tv.setSignName(sign.getName());
        }

        SmsTemplateDO smsTemplateDO = templateService.querySmsTemplateDetail(task.getTemplateId());
        if (null != smsTemplateDO) {
            tv.setTemplateName(smsTemplateDO.getName());
        }

        BeanUtils.copyProperties(task, tv);
        return tv;
    }

    @Override public TaskStatistics queryTaskStatistics(Long id) {
        return null;
    }

    @Override public void modify(TaskParam taskParam) {
        TaskDO task = new TaskDO();
        BeanUtils.copyProperties(taskParam, task);
        taskService.updateTask(task);
    }

    @Override public void changeTaskStatus(Long id) throws BusinessException {
        taskService.changeTaskStatus(id);
    }
}
