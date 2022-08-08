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
package com.tarena.dispatcher.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("dispatcher")
public class DispatcherConfig {
    private Boolean assemblerEmail;
    private Boolean assemblerSms;
    private Boolean noticeSmsAli;
    private Boolean noticeEmailAli;
    private String smsAliPigeonProviderCode;
    private Boolean enableReceipt;

    public Boolean getAssemblerEmail() {
        return assemblerEmail;
    }

    public void setAssemblerEmail(Boolean assemblerEmail) {
        this.assemblerEmail = assemblerEmail;
    }

    public Boolean getAssemblerSms() {
        return assemblerSms;
    }

    public void setAssemblerSms(Boolean assemblerSms) {
        this.assemblerSms = assemblerSms;
    }

    public String getSmsAliPigeonProviderCode() {
        return smsAliPigeonProviderCode;
    }

    public void setSmsAliPigeonProviderCode(String smsAliPigeonProviderCode) {
        this.smsAliPigeonProviderCode = smsAliPigeonProviderCode;
    }

    public Boolean getNoticeSmsAli() {
        return noticeSmsAli;
    }

    public void setNoticeSmsAli(Boolean noticeSmsAli) {
        this.noticeSmsAli = noticeSmsAli;
    }

    public Boolean getNoticeEmailAli() {
        return noticeEmailAli;
    }

    public void setNoticeEmailAli(Boolean noticeEmailAli) {
        this.noticeEmailAli = noticeEmailAli;
    }

    public Boolean getEnableReceipt() {
        return enableReceipt;
    }

    public void setEnableReceipt(Boolean enableReceipt) {
        this.enableReceipt = enableReceipt;
    }
}