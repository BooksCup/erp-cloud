package com.bc.contract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 合同
 *
 * @author zhou
 */
@EnableDiscoveryClient
@RefreshScope
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"com.bc"})
public class ContractProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractProviderApplication.class, args);
    }

}