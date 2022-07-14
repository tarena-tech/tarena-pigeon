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

import com.alibaba.fastjson.JSONObject;
import com.tarena.dispatcher.NoticeEventGetter;
import com.tarena.dispatcher.assemble.impl.TargetAssemblerRegistry;
import com.tarena.dispatcher.respository.TaskRepository;
import com.tarena.dispatcher.storage.entity.SmsSignDO;
import com.tarena.dispatcher.storage.entity.TaskDO;
import com.tarena.dispatcher.storage.entity.TaskTargetDO;
import com.tarena.dispatcher.storage.entity.TemplateDO;
import com.tarena.mnmp.api.NoticeDTO;
import com.tarena.mnmp.api.TargetDTO;
import com.tarena.mnmp.enums.NoticeType;
import com.tarena.mnmp.enums.SendType;
import com.tarena.mnmp.enums.TaskStatus;
import com.tarena.schedule.utils.NoticeTaskTrigger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public abstract class AbstractScheduler {

    private static Logger logger = LoggerFactory.getLogger(AbstractScheduler.class);

    public static final String DATEFORMATSECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String DATEFORMATMIN = "yyyy-MM-dd HH:mm";

    @Autowired
    private TaskRepository taskRepository;

    //abstract List<NoticeTaskTrigger> queryTriggers();
    //abstract List<List<TargetDTO>> getTargets(NoticeTaskTrigger noticeTaskTrigger);

    abstract boolean stop();

    abstract <T extends NoticeEventGetter> void send(T noticeEvent) throws Throwable;

    /**
     * 组装任务发送需要的内容
     *
     * @param taskTrigger
     * @param targets
     * @return
     */
    private List<NoticeDTO> assemble(NoticeTaskTrigger taskTrigger, List<List<TargetDTO>> targets) {
        List<NoticeDTO> notices = new ArrayList<>();
        for (List<TargetDTO> targetList : targets) {
            NoticeDTO notice = new NoticeDTO();
            notice.setTaskId(taskTrigger.getTaskId());
            notice.setTriggerTime(new SimpleDateFormat(DATEFORMATSECOND).format(taskTrigger.getFirstTriggerTime()));
            notice.setNoticeType(NoticeType.SMS);
            notice.setTargets(targetList);
            TemplateDO template = taskRepository.queryTemplate(taskTrigger.getTemplateId());
            notice.setTemplateCode(template.getCode());
            notice.setTemplateContent(template.getContent());
            notice.setAppCode(template.getAppCode());
            SmsSignDO sign = taskRepository.querySign(taskTrigger.getSignId());
            notice.setSignName(sign.getName());
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
                        if (SendType.notImmediately(trigger.getTaskType())) {
                            String triggerTime = new SimpleDateFormat(DATEFORMATMIN).format(trigger.getNextTriggerTime());
                            String nowTime = new SimpleDateFormat(DATEFORMATMIN).format(new Date());
                            if (!triggerTime.equals(nowTime)) {
                                return;
                            }
                        }
                        sendByTask(trigger);
                    }
                }
                if (CollectionUtils.isEmpty(triggers)) {
                    Thread.sleep(1000L);
                }
            } catch (Throwable e) {
                logger.error("schedule error ", e);
            }
        }
    }

    private void sendByTask(NoticeTaskTrigger trigger) throws Throwable {
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
            this.taskRepository.updateNextTriggerTime(trigger.getTaskId(), nextTriggerTime);
            this.taskRepository.updateTaskStatus(trigger.getTaskId(), TaskStatus.TASK_DOING.status());
        }
    }

    /**
     * 查询需要执行的任务列表
     *
     * @return
     */
    private List<NoticeTaskTrigger> queryTriggers() {
        List<TaskDO> taskList = taskRepository.queryTriggers();
        if (CollectionUtils.isEmpty(taskList)) {
            return null;
        }
        List<NoticeTaskTrigger> resList = taskList.stream().map(task -> {
            NoticeTaskTrigger trigger = new NoticeTaskTrigger();
            BeanUtils.copyProperties(task, trigger);
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
        List<List<TargetDTO>> targetsList = new ArrayList<>();
        List<TargetDTO> targets = new ArrayList<>();
        List<TaskTargetDO> taskTargetList = taskRepository.getTargets(noticeTaskTrigger.getTaskId());
        taskTargetList.forEach(target -> {
            targets.add(new TargetDTO(target.getTarget(), JSONObject.parseObject(target.getParams(), Map.class)));
        });
        targetsList.add(targets);
        return targetsList;
    }
}
