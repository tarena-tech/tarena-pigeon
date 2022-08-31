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
import com.tarena.dispatcher.properties.LimitProperties;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
@Order(0)
public class LimitAppInterceptor implements ISendInterceptor {


    @Resource
    private LimitProperties limitProperties;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static Logger logger = LoggerFactory.getLogger(LimitAppInterceptor.class);

    public static final String LIMIT_REDIS_PRE = "pigeon:";

    @Override public void before(SmsTarget smsTarget) throws BusinessException {
        String app = smsTarget.getAppCode();
        String format = DateFormatUtils.format(new Date(), "yyyy:HH:dd");
        String key = LIMIT_REDIS_PRE + app + ":" + format;

        if (null != limitProperties.getAppDayCount() && limitProperties.getAppDayCount().compareTo(0L) > 0) {
            Long redisCount = stringRedisTemplate.opsForValue().increment(key);
            redisCount = Optional.ofNullable(redisCount).orElse(0L);
            stringRedisTemplate.expire(key, 1, TimeUnit.DAYS);

            if (redisCount.compareTo(limitProperties.getAppDayCount()) > 0) {
                logger.warn("超过限制, app_code: {}", app);
                throw new BusinessException("100", "超过app每日限制,请明日重试");
            }
        }
    }


}
