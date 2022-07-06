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

package com.tarena.dispatcher.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.event.SmsNoticeEvent;
import com.tarena.mnmp.enums.NoticeType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsAliNoticeDispatcher extends AbstractNoticeDispatcher<SmsNoticeEvent> {
    private static Logger logger = LoggerFactory.getLogger(SmsAliNoticeDispatcher.class);

    public String getNoticeType() {
        return NoticeType.SMS.name().toLowerCase() + "Ali";
    }

    @Override
    public void dispatcher(SmsNoticeEvent notice) {
        logger.info("sms-ali dispatcher");
        Config config = new Config()
            // 您的 AccessKey ID
            .setAccessKeyId("LTAI5tMCbgQhRZTpFDHTpjdS")
            // 您的 AccessKey Secret
            .setAccessKeySecret("u8q0vU62VSH0rsoWHyuN5EF07ScIB1");
//        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        Client client = null;
        try {
            client = new Client(config);
        } catch (Exception e) {
            logger.info("create ali cloud message client failed,due to:{}" + e.getMessage());
            e.printStackTrace();
        }
        List<SmsTarget> targets = notice.getTargets();
        for (SmsTarget smsTarget : targets) {

            SendSmsRequest sendSmsRequest = new SendSmsRequest();
            sendSmsRequest.setPhoneNumbers(smsTarget.getTarget());
            sendSmsRequest.setTemplateCode(smsTarget.getTemplateCode());
            sendSmsRequest.setTemplateParam(smsTarget.getTemplateParam());
            sendSmsRequest.setSignName(smsTarget.getSignName());
            RuntimeOptions runtime = new RuntimeOptions();
            try {
                // 复制代码运行请自行打印 API 的返回值
                SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
                SendSmsResponseBody body = sendSmsResponse.getBody();
                logger.info(body.getCode() + "/" + body.getMessage() + "/" + body.getRequestId());
            } catch (TeaException error) {
                // 如有需要，请打印 error
                com.aliyun.teautil.Common.assertAsString(error.message);
            } catch (Exception _error) {
                TeaException error = new TeaException(_error.getMessage(), _error);
                // 如有需要，请打印 error
                com.aliyun.teautil.Common.assertAsString(error.message);
            }
        }
    }
}
