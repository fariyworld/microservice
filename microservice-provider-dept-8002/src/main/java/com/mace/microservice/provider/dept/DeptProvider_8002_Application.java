package com.mace.microservice.provider.dept;

import com.mace.microservice.common.base.UniversalMenthod;
import com.terran4j.commons.api2doc.config.EnableApi2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/**
 * description:
 * <br />
 * Created by mace on 17:11 2018/7/16.
 */
@SpringBootApplication(scanBasePackages = "com.mace.microservice")
@EnableApi2Doc//启用 Api2Doc 服务 http://你的项目地址/api2doc/home.html
@EnableEurekaClient//本服务启动后会自动注册进eureka服务中
@ImportResource("classpath:spring-tr.xml")//启用模糊匹配的事务管理
public class DeptProvider_8002_Application {

    public static void main(String[] args) {

        SpringApplication.run(DeptProvider_8002_Application.class, args);
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){

        return UniversalMenthod.fastJsonHttpMessageConverters();
    }
}
