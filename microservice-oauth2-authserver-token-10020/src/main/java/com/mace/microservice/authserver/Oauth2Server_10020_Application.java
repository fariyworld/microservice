package com.mace.microservice.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * description:
 * <br />
 * Created by mace on 10:52 2018/7/22.
 */
@SpringBootApplication(scanBasePackages = "com.mace.microservice")
//@EnableEurekaClient//本服务启动后会自动注册进eureka服务中
public class Oauth2Server_10020_Application {

    public static void main(String[] args) {

        SpringApplication.run(Oauth2Server_10020_Application.class,args);
    }
}
