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

import com.alibaba.fastjson.JSON;
import com.tarena.dispatcher.sender.HuaWeiResult;
import com.tarena.dispatcher.sender.HwSmsReq;
import com.tarena.mnmp.protocol.BusinessException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HuanWeiSmsClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";

    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

    private static final String URL = "https://smsapi.cn-north-4.myhuaweicloud.com:443/sms/batchSendSms/v1";

    private static final String P_PREFIX = "+86";

    private RestTemplate restTemplate;

    private HttpHeaders headers;

    private HuanWeiSmsClient() {
        throw new RuntimeException("配置错误");
    }

    public HuanWeiSmsClient(String appKey, String appSecret, RestTemplate restTemplate) {
        String wsseHeader = buildWsseHeader(appKey, appSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.put("Content-Type", Arrays.asList("application/x-www-form-urlencoded"));
        headers.put("Authorization", Arrays.asList(AUTH_HEADER_VALUE));
        headers.put("X-WSSE", Arrays.asList(wsseHeader));
        this.headers = headers;
        this.restTemplate = restTemplate;
    }

    private String buildWsseHeader(String appKey, String appSecret) {
        if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
            logger.error("buildWsseHeader(): appKey or appSecret is null.");
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date());
        String nonce = UUID.randomUUID().toString().replace("-", "");

        MessageDigest md;
        byte[] passwordDigest = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update((nonce + time + appSecret).getBytes());
            passwordDigest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(passwordDigest);
        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    private String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
        String statusCallBack, String signature) {
        if (null == sender || null == receiver || null == templateId || sender.isEmpty() || receiver.isEmpty()
            || templateId.isEmpty()) {
            logger.error("buildRequestBody(): sender, receiver or templateId is null.");
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();

        map.put("from", sender);
        map.put("to", receiver);
        map.put("templateId", templateId);
        if (null != templateParas && !templateParas.isEmpty()) {
            map.put("templateParas", templateParas);
        }
        if (null != statusCallBack && !statusCallBack.isEmpty()) {
            map.put("statusCallback", statusCallBack);
        }
        if (null != signature && !signature.isEmpty()) {
            map.put("signature", signature);
        }

        StringBuilder sb = new StringBuilder();
        String temp = "";

        for (String s : map.keySet()) {
            try {
                temp = URLEncoder.encode(map.get(s), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sb.append(s).append("=").append(temp).append("&");
        }

        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public HuaWeiResult send(HwSmsReq req) throws BusinessException {
        String sender = "1069368924410003549";
        String receiver = req.getPhone();
        if (!receiver.startsWith(P_PREFIX)) {
            receiver = P_PREFIX + receiver;
        }

        String body = buildRequestBody(sender, receiver, req.getTemplateCode(), req.getContent(),

            null, req.getSignName());
        logger.info("send hw body:{}",body);
        HttpEntity<String> ectity = new HttpEntity<>(body, headers);
        logger.info("send entity :{}",JSON.toJSONString(ectity));
        ResponseEntity<HuaWeiResult> exchange = restTemplate.exchange(URL, HttpMethod.POST, ectity, HuaWeiResult.class);
        if (!HttpStatus.OK.equals(exchange.getStatusCode())) {
            logger.error("短信发送失败, msg: {}", JSON.toJSONString(exchange));
            throw new BusinessException("100", "短信发送失败");
        }
        return exchange.getBody();
    }
}
