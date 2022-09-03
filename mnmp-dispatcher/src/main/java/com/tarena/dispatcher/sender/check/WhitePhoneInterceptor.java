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

package com.tarena.dispatcher.sender.check;

import com.tarena.dispatcher.SmsTarget;
import com.tarena.mnmp.constant.Constant;
import com.tarena.mnmp.protocol.BusinessException;
import java.io.Serializable;
import javax.annotation.Resource;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Order(Integer.MIN_VALUE)
@Service
public class WhitePhoneInterceptor implements ISendInterceptor {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override public void before(SmsTarget smsTarget) throws BusinessException {
        Boolean b = redisTemplate.opsForHash().hasKey(Constant.WHITE_PHONE + smsTarget.getAppCode(), smsTarget.getTarget());
        if (!Boolean.TRUE.equals(b)) {
            throw new BusinessException("100", "该手机号未在白名单中");
        }

    }
}
