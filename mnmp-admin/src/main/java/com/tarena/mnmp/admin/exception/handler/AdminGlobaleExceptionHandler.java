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

package com.tarena.mnmp.admin.exception.handler;

import com.tarena.mnmp.admin.exception.AdminException;
import com.tarena.mnmp.commons.protocol.BusinessException;
import com.tarena.mnmp.commons.protocol.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.tarena.mnmp.admin.controller")
public class AdminGlobaleExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(AdminGlobaleExceptionHandler.class);
    /**
     * 处理业务异常
     */
    @ExceptionHandler({AdminException.class})
    public Result handleCoolSharkServiceException(AdminException e) {
        logger.debug("出现业务异常，业务错误码={}，描述文本={}", e.getCode(), e.getMessage());
        e.printStackTrace();
        return Result.fail(e);
    }

    /**
     * 处理绑定异常（通过Validation框架验证请求参数时的异常）
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        logger.debug("验证请求数据时出现异常：{}", e.getClass().getName());
        e.printStackTrace();
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return new Result(message);
    }

    /**
     * 处理系统（其它）异常
     */
    @ExceptionHandler({Throwable.class})
    public Result handleSystemError(Throwable e) {
        logger.debug("出现系统异常，异常类型={}，描述文本={}", e.getClass().getName(), e.getMessage());
        e.printStackTrace();
        return Result.fail();
    }
}
