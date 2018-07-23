package com.mace.microservice.consumer.dept;

import com.mace.microservice.common.base.UniversalMenthod;
import com.mace.microservice.consumer.dept.config.RibbnoClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

/**
 * description:
 * <br />
 * Created by mace on 16:45 2018/7/16.
 */
@SpringBootApplication
@EnableDiscoveryClient//向服务中心注册
@RibbonClient(name= "CLOUD-PROVIDER-DEPT"/*, configuration = RibbnoClientConfig.class*/)//自定义Ribbon负载均衡,不能在主类的包及子包下
@EnableCircuitBreaker//启用断路器
public class DeptConsumerApplication {

    public static void main(String[] args) {

        SpringApplication.run(DeptConsumerApplication.class, args);
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){

        return UniversalMenthod.fastJsonHttpMessageConverters();
    }
}
