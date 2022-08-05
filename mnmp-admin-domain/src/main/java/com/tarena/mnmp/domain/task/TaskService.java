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

package com.tarena.mnmp.domain.task;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.tarena.mnmp.commons.json.Json;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.commons.utils.DateUtils;
import com.tarena.mnmp.commons.utils.RegexUtils;
import com.tarena.mnmp.domain.TaskDO;
import com.tarena.mnmp.domain.TaskTargetDO;
import com.tarena.mnmp.domain.ttarget.TaskTargetService;
import com.tarena.mnmp.enums.AuditStatus;
import com.tarena.mnmp.enums.Deleted;
import com.tarena.mnmp.enums.TaskStatus;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    @Resource
    private TaskTargetService taskTargetService;

    @Autowired
    private Json json;

    @Value("${excel.path}")
    private String excelPath;

    public void doAudit(Long id, Integer status, String result) throws BusinessException {
        TaskDO taskDO = taskDao.queryById(id);
        if (null == taskDO) {
            throw new BusinessException("100", "任务不存在");
        }
        taskDO.setId(id);
        taskDO.setAuditStatus(status);
        taskDO.setAuditResult(result);
        if (AuditStatus.REJECT.getStatus().intValue() == status) {
            taskDao.update(taskDO);
            return;
        }

        Date date = DateUtils.generateNextTriggerTime(taskDO.getCycleLevel(), taskDO.getCycleNum(), new Date());
        taskDO.setNextTriggerTime(date);
        taskDao.update(taskDO);
    }

    public List<TargetExcelData> addTask(TaskDO taskDO, String filePath) {
        taskDO.setTaskStatus(TaskStatus.TASK_NO_OPEN.status());
        taskDO.setAuditStatus(AuditStatus.WAITING.getStatus());
        taskDO.setTargetFileUrl(filePath);
        taskDO.setTargetFileName(filePath.substring(filePath.lastIndexOf("/") + 1));
        taskDO.setCreateTime(new Date());
        taskDO.setUpdateTime(new Date());
        taskDao.save(taskDO);
        List<TargetExcelData> fails = new ArrayList<>();
        String readExcelPath = excelPath + filePath;
        log.info("addTask taskDO:{}, readExcelPath:{}", taskDO, readExcelPath);
        try {
            Set<String> set = new HashSet<>();
            EasyExcel.read(readExcelPath, TargetExcelData.class, new ReadListener<TargetExcelData>() {

                public static final int BATCH_COUNT = 100;

                private List<TargetExcelData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                @Override public void invoke(TargetExcelData data, AnalysisContext context) {
                    cachedDataList.add(data);
                    if (cachedDataList.size() >= BATCH_COUNT) {
                        save();
                    }
                }

                @Override public void doAfterAllAnalysed(AnalysisContext context) {
                    save();
                }

                private void save() {
                    List<TaskTargetDO> list = new ArrayList<>();
                    for (TargetExcelData old : cachedDataList) {
                        if (!RegexUtils.checkPhone(old.getPhone())) {
                            fails.add(old);
                            continue;
                        }
                        try {
                            json.parse(old.getParams());
                        } catch (Exception e) {
                            fails.add(old);
                            continue;
                        }

                        if (set.contains(old.getPhone())) {
                            continue;
                        }

                        TaskTargetDO task = new TaskTargetDO();
                        task.setTaskId(taskDO.getId());
                        task.setDeleted(Deleted.NO.getVal());
                        task.setParams(old.getParams());
                        task.setTarget(old.getPhone());
                        list.add(task);
                        set.add(old.getPhone());
                    }
                    taskTargetService.saveBatch(list);
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }).sheet().doRead();
        } catch (Exception e) {
            taskDao.deleteById(taskDO.getId());
        }

        return fails;
    }

    public void action(Long id, int status) {
        TaskDO task = new TaskDO();
        task.setId(id);
        task.setTaskStatus(status);
        taskDao.update(task);
    }

    public void updateTask(TaskDO task) {
        task.setAuditStatus(AuditStatus.WAITING.getStatus());
        taskDao.update(task);
    }

    public PagerResult<TaskDO> queryListByPage(TaskQuery query) {

        List<TaskDO> list = taskDao.queryList(query);
        Long count = taskDao.queryCount(query);

        PagerResult<TaskDO> res = new PagerResult<>(query.getPageSize(), query.getCurrentPageIndex());
        res.setList(list);
        res.setRecordCount(Optional.ofNullable(count).orElse(0L));
        return res;
    }

    public TaskDO detailById(Long id) {
        return taskDao.queryById(id);
    }

    public void changeTaskStatus(Long id) throws BusinessException {
        TaskDO aDo = detailById(id);
        if (null == aDo) {
            throw new BusinessException("100", "任务不存在");
        }
        int open = 0;
        if (Objects.equals(TaskStatus.TASK_STOP.status(), aDo.getTaskStatus())
            || Objects.equals(TaskStatus.TASK_END.status(), aDo.getTaskStatus())) {
            open = 1;
        }

        TaskDO up = new TaskDO();
        up.setId(id);
        if (open == 1) {
            up.setTaskStatus(TaskStatus.TASK_NO_OPEN.status());
            up.setNextTriggerTime(DateUtils.generateNextTriggerTime(up.getCycleLevel(), up.getCycleNum(), new Date()));
        } else {
            up.setTaskStatus(TaskStatus.TASK_END.status());
            up.setTriggerEndTime(new Date());
        }
        taskDao.update(up);
    }
}
