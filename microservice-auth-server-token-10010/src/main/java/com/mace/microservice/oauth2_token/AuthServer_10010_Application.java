package com.mace.microservice.oauth2_token;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * description:
 * <br />
 * Created by mace on 11:13 2018/7/20.
 */
@SpringBootApplication(scanBasePackages = "com.mace.microservice")
@EnableEurekaClient//本服务启动后会自动注册进eureka服务中
@EnableAuthorizationServer//开启授权服务器
public class AuthServer_10010_Application {

    public static void main(String[] args) {

        SpringApplication.run(AuthServer_10010_Application.class,args);
    }
}
