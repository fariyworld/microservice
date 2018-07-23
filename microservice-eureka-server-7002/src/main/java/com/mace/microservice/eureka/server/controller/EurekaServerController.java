package com.mace.microservice.eureka.server.controller;

import com.mace.microservice.common.base.RestPackResponse;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description:
 * <br />
 * Created by mace on 16:20 2018/7/16.
 */
@RestController
@RequestMapping("/eureka02/server")
@Slf4j
public class EurekaServerController {

    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping(value = "/discovery.do")
    public RestPackResponse<DiscoveryClient> discovery(){

        List<String> services = discoveryClient.getServices();

        log.info("注册到 eureka 的服务有：", services);

        for (String service : services) {

            List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(service.toUpperCase());

            for (ServiceInstance serviceInstance : serviceInstanceList) {

                log.info(serviceInstance.getServiceId() + "\t" +
                        serviceInstance.getHost() + "\t" +
                        serviceInstance.getPort() + "\t" +
                        serviceInstance.getUri());
            }
        }

        return RestPackResponse.createBySuccess("服务发现成功", this.discoveryClient);
    }
}
