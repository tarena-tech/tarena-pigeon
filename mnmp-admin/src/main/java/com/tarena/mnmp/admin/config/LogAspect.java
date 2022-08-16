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
package com.tarena.mnmp.admin.config;

import com.tarena.mnmp.admin.utils.IPUtils;
import com.tarena.mnmp.commons.json.Json;
import java.io.Serializable;
import java.lang.reflect.Method;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private Json json;

    @Pointcut("execution(* com.tarena.mnmp.admin.controller..*.*(..))")
    public void controllerCut() {
    }


    @Around("controllerCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        String methodName = point.getTarget().getClass().getName() + "." + signature.getName();

        Method method = signature.getMethod();

        StringBuilder params = params(point, method);
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        String httMethod = null;
        String ip = null;
        if (null != attributes) {
            HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
            httMethod = request.getMethod();
            ip = IPUtils.getIpAddress(request);
        }


        log.info("request  ip: {}, http_method: {}, method:{}, reqParam:{}", ip, httMethod, methodName, params);
        Object proceed = point.proceed();
        log.info("response, res: {}", json.toString((Serializable) proceed));
        return proceed;
    }

    private StringBuilder params(ProceedingJoinPoint point, Method method) {
        Object[] args = point.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        StringBuilder params = new StringBuilder();
        if (args != null && paramNames != null) {
            for (int i = 0; i < args.length; i++) {
                Object param = args[i];
                if (ignoreParam(param)) {
                    continue;
                }
                params.append("  ").append(paramNames[i]).append(": ").append(param);
            }
        }
        return params;
    }


    private boolean ignoreParam(Object param) {
        return param instanceof ServletResponse || param instanceof ServletRequest || param instanceof MultipartFile;
    }
}
