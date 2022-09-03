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
import com.tarena.mnmp.commons.bo.PhoneWhiteListBO;
import com.tarena.mnmp.commons.utils.RedisKeyUtils;
import com.tarena.mnmp.constant.Constant;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.protocol.BusinessException;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
        String hashKey = RedisKeyUtils.phoneWhiteHashKey(smsTarget.getAppCode(), smsTarget.getTarget());
        PhoneWhiteListBO value = (PhoneWhiteListBO) redisTemplate.opsForHash().get(Constant.WHITE_PHONE, hashKey);
        if (null == value) {
            throw new BusinessException("100", "该手机号未在白名单中");
        }

        if (Objects.equals(Enabled.NO.getVal(), value.getIsEnabled())) {
            throw new BusinessException("101", "该手机号未启用");
        }

        Date now = new Date();

        if (null == value.getStartTime() || now.before(value.getStartTime())) {
            throw new BusinessException("102", "该手机号未到生效时间");
        }
        if (null == value.getEndTime() || now.after(value.getEndTime())) {
            throw new BusinessException("103", "该手机号已超过生效时间");
        }
    }
}
