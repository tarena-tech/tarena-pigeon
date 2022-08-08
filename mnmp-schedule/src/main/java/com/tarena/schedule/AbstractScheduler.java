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

package com.tarena.schedule;

import com.google.common.collect.Lists;
import com.tarena.dispatcher.NoticeEventGetter;
import com.tarena.dispatcher.assemble.impl.TargetAssemblerRegistry;
import com.tarena.dispatcher.bo.SmsSignBO;
import com.tarena.dispatcher.bo.TaskBO;
import com.tarena.dispatcher.bo.TaskTargetBO;
import com.tarena.dispatcher.bo.TemplateBO;
import com.tarena.dispatcher.respository.TaskRepository;
import com.tarena.mnmp.api.NoticeDTO;
import com.tarena.mnmp.api.TargetDTO;
import com.tarena.mnmp.commons.json.Json;
import com.tarena.mnmp.constant.Constant;
import com.tarena.mnmp.constant.ErrorCode;
import com.tarena.mnmp.enums.NoticeType;
import com.tarena.mnmp.enums.SendType;
import com.tarena.mnmp.enums.TaskStatus;
import com.tarena.mnmp.monitor.Monitor;
import com.tarena.schedule.utils.NoticeTaskTrigger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public abstract class AbstractScheduler {

    private static Logger logger = LoggerFactory.getLogger(AbstractScheduler.class);
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    protected Monitor monitor;

    @Autowired
    private Json json;

    abstract boolean stop();

    abstract <T extends NoticeEventGetter> void send(T noticeEvent) throws Exception;

    /**
     * 组装任务发送需要的内容
     *
     * @param taskTrigger
     * @param targets
     * @return
     */
    private List<NoticeDTO> assemble(NoticeTaskTrigger taskTrigger, List<List<TargetDTO>> targets) {
        TemplateBO template = taskRepository.queryTemplate(taskTrigger.getTemplateId());
        SmsSignBO sign = taskRepository.querySign(taskTrigger.getSignId());

        List<NoticeDTO> notices = new ArrayList<>();
        for (List<TargetDTO> targetList : targets) {
            NoticeDTO notice = new NoticeDTO();
            notice.setTaskId(taskTrigger.getTaskId());
            notice.setMock(taskTrigger.getMock());
            //每次任务执行的时间
            notice.setTriggerTime(new SimpleDateFormat(Constant.DATE_FORMAT_MIN).format(taskTrigger.getNextTriggerTime()));
            notice.setNoticeType(NoticeType.SMS);
            notice.setSendType(SendType.getEnum(template.getTemplateType()));
            notice.setTargets(targetList);
            notice.setTemplateCode(template.getCode());
            notice.setTemplateContent(template.getContent());
            notice.setAppCode(template.getAppCode());
            notice.setSignName(sign.getName());
            notice.setSignCode(sign.getName());
            notices.add(notice);
        }
        return notices;
    }

    public void schedule() {
        while (!stop()) {
            try {
                List<NoticeTaskTrigger> triggers = queryTriggers();
                if (!CollectionUtils.isEmpty(triggers)) {
                    for (NoticeTaskTrigger trigger : triggers) {
                        sendByTask(trigger);
                    }
                }
                if (CollectionUtils.isEmpty(triggers)) {
                    Thread.sleep(1000L);
                }
            } catch (Throwable e) {
                this.monitor.alarms(ErrorCode.SCHEDULE_ERROR, e.getMessage());
                logger.error("schedule error ", e);
            }
        }
    }

    private void sendByTask(NoticeTaskTrigger trigger) {
        try {
            List<List<TargetDTO>> targetsList = this.getTargets(trigger);
            List<NoticeDTO> notices = this.assemble(trigger, targetsList);
            int batchIndex = 0;
            for (NoticeDTO notice : notices) {
                this.send(TargetAssemblerRegistry.getInstance().assemble(notice, batchIndex++));
            }
            Date nextTriggerTime = trigger.generateNextTriggerTime();
            if (trigger.isFinish(nextTriggerTime)) {
                this.taskRepository.finishTask(trigger.getTaskId(), TaskStatus.TASK_END.status());
            } else {
                this.taskRepository.updateNextTriggerTimeAndStatus(trigger.getTaskId(), nextTriggerTime, TaskStatus.TASK_DOING.status());
            }
        } catch (Exception e) {
            this.monitor.alarms(ErrorCode.DISPATCHER_TASK_ERROR, e.getMessage());
            this.taskRepository.errorTask(trigger.getTaskId(), TaskStatus.TASK_ERROR.status(), e.getMessage());
            logger.error("task-id {} app-id {} ", trigger.getTaskId(), trigger.getAppId(), e);
        }
    }

    /**
     * 查询需要执行的任务列表
     *
     * @return
     */
    private List<NoticeTaskTrigger> queryTriggers() {
        List<TaskBO> taskList = taskRepository.queryTriggers();
        if (CollectionUtils.isEmpty(taskList)) {
            return null;
        }
        List<NoticeTaskTrigger> resList = taskList.stream().map(task -> {
            NoticeTaskTrigger trigger = new NoticeTaskTrigger();
            BeanUtils.copyProperties(task, trigger);
            trigger.setTaskId(task.getId());
            trigger.setMock(task.getMock());
            return trigger;
        }).collect(Collectors.toList());
        return resList;
    }

    /**
     * 根据任务获取对应的目标信息
     *
     * @param noticeTaskTrigger
     * @return
     */
    private List<List<TargetDTO>> getTargets(NoticeTaskTrigger noticeTaskTrigger) {
        List<TargetDTO> targets = new ArrayList<>();
        //todo  分页查询优化
        List<TaskTargetBO> taskTargetList = taskRepository.getTargets(noticeTaskTrigger.getTaskId());
        taskTargetList.forEach(target -> {
            targets.add(new TargetDTO(target.getTarget(), this.json.parse(target.getParams())));
        });
        return Lists.partition(targets, 1000);
    }
}
