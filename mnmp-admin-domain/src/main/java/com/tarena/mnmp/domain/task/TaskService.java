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
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.commons.utils.DateUtils;
import com.tarena.mnmp.domain.TaskDO;
import com.tarena.mnmp.domain.TaskTargetDO;
import com.tarena.mnmp.domain.ttarget.TaskTargetService;
import com.tarena.mnmp.enums.AuditStatus;
import com.tarena.mnmp.enums.Deleted;
import com.tarena.mnmp.enums.TaskStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    @Resource
    private TaskTargetService taskTargetService;

    public void doAudit(Long id, Integer status, String result) {
        TaskDO taskDO = taskDao.queryById(id);
        if (null == taskDO) {
            return;
        }
        taskDO.setId(id);
        taskDO.setTaskAudit(status);
        taskDO.setTaskAuditResult(result);
        if (AuditStatus.REJECT.getStatus().intValue() == status) {
            taskDao.update(taskDO);
            return;
        }

        Date date = DateUtils.generateNextTriggerTime(taskDO.getCycleLevel(), taskDO.getCycleNum(), new Date());
        taskDO.setNextTriggerTime(date);
        taskDao.update(taskDO);
    }

    public void addTask(TaskDO bo, String filePath) {
        bo.setTaskStatus(TaskStatus.TASK_NO_OPEN.status());
        bo.setTaskAudit(AuditStatus.WAITING.getStatus());
        bo.setTargetFileName("todo");
        taskDao.save(bo);
        // 就用官方的demo，ReadListener每次必须new 所以说 在哪里写都一样
        EasyExcel.read(filePath, TargetExcelData.class, new ReadListener<TargetExcelData>() {

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
                cachedDataList.forEach(old -> {
                    TaskTargetDO task = new TaskTargetDO();
                    task.setTaskId(bo.getId());
                    task.setDeleted(Deleted.NO.getVal());
                    task.setParams(old.getParams());
                    task.setTarget(old.getPhone());
                    list.add(task);
                });
                taskTargetService.saveBatch(list);
                // 存储完成清理 list
                cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            }
        }).sheet().doRead();

    }

    public void action(Long id, int status) {
        TaskDO task = new TaskDO();
        task.setId(id);
        task.setTaskStatus(status);
        taskDao.update(task);
    }

    public void updateTask(TaskDO task) {
        task.setTaskAudit(AuditStatus.WAITING.getStatus());
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
        if (null == id) {
            return new TaskDO();
        }
        return taskDao.queryById(id);
    }
}
