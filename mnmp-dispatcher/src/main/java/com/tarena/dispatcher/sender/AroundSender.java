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

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.sender.check.ISendInterceptor;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AroundSender {

    private static Logger logger = LoggerFactory.getLogger(AroundSender.class);


    @Resource
    private List<ISendInterceptor> sendInterceptors;


    public final String aroundSend(SmsTarget smsTarget) throws BlockException, BusinessException {
        checkBefore(smsTarget);
        Entry appEntry = null;
        Entry phoneEntry = null;
        try {
            appEntry = SphU.entry("sendAppCodeLimit", EntryType.IN, 1, smsTarget.getAppCode());
            phoneEntry = SphU.entry("sendPhoneLimit", EntryType.IN, 1, smsTarget.getTarget());
            return doSend(smsTarget);
        } catch (BlockException exception) {
            logger.error("触发限流: ", exception);
            throw exception;
        } catch (Exception e) {
            logger.error("异常信息: ", e);
            throw new RuntimeException(e);
        } finally {
            if (null != phoneEntry) {
                phoneEntry.exit(1, smsTarget.getAppCode());
            }
            if (null != appEntry) {
                appEntry.exit(1, smsTarget.getAppCode());
            }
            for (ISendInterceptor interceptor : sendInterceptors) {
                try {
                    interceptor.after(smsTarget);
                } catch (Exception e) {
                    // ignore
                    logger.error("error:", e);
                }
            }
        }

    }

    public abstract String doSend(SmsTarget smsTarget) throws Exception;

    public final void checkBefore(SmsTarget smsTarget) throws BusinessException {
        for (ISendInterceptor interceptor : sendInterceptors) {
            interceptor.before(smsTarget);
        }
    }


}
