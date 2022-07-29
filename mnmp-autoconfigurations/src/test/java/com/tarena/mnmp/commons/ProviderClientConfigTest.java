package com.tarena.mnmp.commons;

import com.alibaba.fastjson2.JSON;
import com.tarena.mnmp.protocol.ProviderClientConfig;

public class ProviderClientConfigTest {
    public static void main(String[] args) {
        ProviderClientConfig providerClientConfig = new ProviderClientConfig();
        providerClientConfig.setDefaultTemplate("SMS_154950909");
        providerClientConfig.setAccessKeyId("LTAI5tJJitoxR8C6FbLNAzVr");
        providerClientConfig.setAccessKeySecret("06GdY3yM3Ies27LxpEUTx1MLrvwgtU");
        System.out.println(JSON.toJSONString(providerClientConfig));
    }
}
