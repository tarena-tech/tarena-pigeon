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

package com.tarena.dispatcher.sender;

import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.bo.PhoneBizIdReceiptBO;
import com.tarena.dispatcher.impl.HuanWeiSmsClient;
import com.tarena.dispatcher.respository.TaskRepository;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HuaWeiSender implements SmsSender {

    private static Logger logger = LoggerFactory.getLogger(AliSmsSender.class);

    private String hwTemplateCode;
    protected TaskRepository taskRepository;

    private HuanWeiSmsClient huanWeiSmsClient;

    public void setHwTemplateCode(String hwTemplateCode) {
        this.hwTemplateCode = hwTemplateCode;
    }

    public void setHuanWeiSmsClient(HuanWeiSmsClient huanWeiSmsClient) {
        this.huanWeiSmsClient = huanWeiSmsClient;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override public String send(SmsTarget smsTarget) throws Exception {
        HwSmsReq req = new HwSmsReq();
        req.setTemplateCode(smsTarget.getTemplateCode());
        req.setTemplateParam(smsTarget.getTemplateParam());
        req.setPhone(smsTarget.getTarget());
        req.setSignName(smsTarget.getSignName());
        req.setContent(smsTarget.getContent());
        HuaWeiResult result = this.huanWeiSmsClient.send(req);
        if (null != result && HuaWeiResult.OK.equals(result.getCode())) {
            return result.getResult().get(0).getSmsMsgId();
        }
        throw new BusinessException("100", "短信发送失败");
    }

    @Override public List<PhoneBizIdReceiptBO> fetchReceipt(List<PhoneBizIdReceiptBO> receipts) {
        return new ArrayList<>();
    }
}
