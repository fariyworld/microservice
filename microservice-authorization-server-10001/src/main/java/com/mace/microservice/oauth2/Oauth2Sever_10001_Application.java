package com.mace.microservice.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * description:
 * <br />
 * Created by mace on 13:44 2018/7/17.
 */
@SpringBootApplication(scanBasePackages = "com.mace.microservice")
@EnableEurekaClient//本服务启动后会自动注册进eureka服务中
public class Oauth2Sever_10001_Application {

    public static void main(String[] args) {

        SpringApplication.run(Oauth2Sever_10001_Application.class,args);
    }
}
