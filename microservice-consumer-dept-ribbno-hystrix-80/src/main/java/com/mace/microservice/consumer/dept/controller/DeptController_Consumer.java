package com.mace.microservice.consumer.dept.controller;

import com.mace.microservice.common.base.RestPackResponse;
import com.mace.microservice.common.entity.Dept;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * description:
 * <br />
 * Created by mace on 16:51 2018/7/16.
 */
@RestController
@RequestMapping("/consumer/dept")
public class DeptController_Consumer {

//    private static final String REST_URL_PREFIX = "http://127.0.0.1:9091";
//    在ribbon中它会根据服务名来选择具体的服务实例，根据服务实例在请求的时候会用具体的url替换掉服务名（大写）
    private static final String REST_SERVICE = "http://MICROSERVICE-PROVIDER-DEPT";

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "findAllServiceFallback")
    @GetMapping(value = "/findAll.do")
    public RestPackResponse<List<Dept>> findAll(){

        return restTemplate.getForObject(REST_SERVICE + "/dept/findAll.do", RestPackResponse.class);
    }


    public RestPackResponse<List<Dept>> findAllServiceFallback() {

        return RestPackResponse.createByError("失败", "客户端关闭", null );
    }
}
