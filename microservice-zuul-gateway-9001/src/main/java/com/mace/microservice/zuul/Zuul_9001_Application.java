package com.mace.microservice.zuul;

import com.mace.microservice.common.base.UniversalMenthod;
import com.mace.microservice.zuul.config.AccessFilter;
import com.mace.microservice.zuul.config.GatewayZuulFallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * description:
 * <br />
 * Created by mace on 17:44 2018/7/16.
 */
@EnableDiscoveryClient//向 eureka 注册服务
@EnableZuulProxy//开启Zuul的API网关服务功能
@SpringBootApplication
public class Zuul_9001_Application {

    public static void main(String[] args) {

        SpringApplication.run(Zuul_9001_Application.class, args);
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){

        return UniversalMenthod.fastJsonHttpMessageConverters();
    }

    @Bean
    public AccessFilter accessFilter() {

        return new AccessFilter();
    }


    @Bean
    public GatewayZuulFallback zuulFallback() {

        return new GatewayZuulFallback();
    }
}
