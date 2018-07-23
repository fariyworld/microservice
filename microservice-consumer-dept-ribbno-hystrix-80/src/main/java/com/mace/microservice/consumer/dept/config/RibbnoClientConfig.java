package com.mace.microservice.consumer.dept.config;

import com.mace.microservice.common.base.UniversalMenthod;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * description:
 * <br />
 * Created by mace on 16:48 2018/7/16.
 */
@Configuration
public class RibbnoClientConfig {

//    @Autowired
//    private RestTemplateBuilder builder;

    /**
     * description: RestTemplate 提供了多种便捷访问 Http 服务的方法
     * 是一种简单便捷的访问 restful 服务模板类，是 Spring 提供的用于访问 Rest 服务的客户端模板工具集
     *
     * REST请求地址、请求参数、HTTP响应的类型
     * (url, requestMap, ResponseType.class)
     *
     *
     * <br /><br />
     * create by mace on 2018/6/30 22:46.
     * @param
     * @return: org.springframework.web.client.RestTemplate
     */
    @Bean
    @ConditionalOnMissingBean({RestTemplate.class})
    @LoadBalanced//注解表明这个 restRemplate 开启负载均衡的功能
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){

        return UniversalMenthod.restTemplate(factory);
    }

    @Bean
    @ConditionalOnMissingBean({ClientHttpRequestFactory.class})
    public ClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //ms
        factory.setConnectTimeout(1*1000*60);
        factory.setReadTimeout(1*1000*60);
        return factory;
    }

    /**
     * description: ribbno 负载均衡策略
     * <br /><br />
     * create by mace on 2018/7/3 16:59.
     * @param
     * @return: com.netflix.loadbalancer.IRule
     */
    @Bean
    public IRule myRule()
    {
//        return new RoundRobinRule();//轮询
        return new RandomRule();//达到的目的，用我们重新选择的随机算法替代默认的轮询。
//        return new RetryRule();//重试策略
    }
}
