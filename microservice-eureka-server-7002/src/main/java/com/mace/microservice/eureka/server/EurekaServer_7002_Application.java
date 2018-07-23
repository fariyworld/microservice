package com.mace.microservice.eureka.server;

import com.mace.microservice.common.base.UniversalMenthod;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

/**
 * description:
 * <br />
 * Created by mace on 15:13 2018/7/16.
 */
@SpringBootApplication
@EnableEurekaServer// 开启服务注册中心, 接受其他微服务注册进来     集群 - 02
public class EurekaServer_7002_Application {

    public static void main(String[] args) {

        SpringApplication.run(EurekaServer_7002_Application.class,args);
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){

        return UniversalMenthod.fastJsonHttpMessageConverters();
    }
}
