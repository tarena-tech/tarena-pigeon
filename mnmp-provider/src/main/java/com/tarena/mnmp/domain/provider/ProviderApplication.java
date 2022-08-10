package com.tarena.mnmp.domain.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tarena.dispatcher.repository.impl", "com.tarena.mnmp.domain.provider.controller"})
@MapperScan(basePackages = {"com.tarena.dispatcher.storage.mapper"})
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
