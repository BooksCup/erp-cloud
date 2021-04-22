package com.bc.auth.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 启动类
 *
 * @author zhou
 */
@SpringBootApplication
@EnableEurekaClient
public class Oauth2AuthResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2AuthResourceApplication.class, args);
    }
}
