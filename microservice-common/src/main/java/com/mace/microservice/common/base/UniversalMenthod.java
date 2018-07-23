package com.mace.microservice.common.base;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * description: 抽取一些通用方法
 * <br />
 * Created by mace on 23:13 2018/6/30.
 */
public class UniversalMenthod {


    /**
     * description: SpringBoot 使用 alibaba.fastjson 序列化
     * <br /><br />
     * create by mace on 2018/7/3 12:02.
     * @param
     * @return: org.springframework.boot.autoconfigure.web.HttpMessageConverters
     */
    public static HttpMessageConverters fastJsonHttpMessageConverters(){

        return new HttpMessageConverters(fastJsonHttpMessageConverter());
    }


    /**
     * description: alibaba.fastjson 序列化
     * <br /><br />
     * create by mace on 2018/7/3 12:03.
     * @param
     * @return: com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
     */
    public static FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                //格式化
                SerializerFeature.PrettyFormat,
                //统一转换时间戳: yyyy-MM-dd HH:mm:ss
                SerializerFeature.WriteDateUseDateFormat
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8")));
        mediaTypes.add(new MediaType(MediaType.TEXT_HTML, Charset.defaultCharset()));
        fastConverter.setSupportedMediaTypes(mediaTypes);

        return fastConverter;
    }


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
    public static RestTemplate restTemplate(ClientHttpRequestFactory factory){

//        return builder.build();
//        RestTemplate restTemplate = new RestTemplate();
        RestTemplate restTemplate = new RestTemplate(factory);

        //换上 fastjson
        List<HttpMessageConverter<?>> httpMessageConverterList= restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator=httpMessageConverterList.iterator();
        if(iterator.hasNext()){
            HttpMessageConverter<?> converter=iterator.next();
            //原有的String是ISO-8859-1编码 去掉
            if(converter instanceof StringHttpMessageConverter){
                iterator.remove();
            }

            //由于系统中默认有jackson 在转换json时自动会启用  但是我们不想使用它 可以直接移除或者将fastjson放在首位
            if(converter instanceof GsonHttpMessageConverter || converter instanceof MappingJackson2HttpMessageConverter){
                iterator.remove();
            }

        }

        httpMessageConverterList.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        httpMessageConverterList.add(0,UniversalMenthod.fastJsonHttpMessageConverter());

        return restTemplate;
    }


    /**
     * description: 获取Ip地址
     * <br /><br />
     * create by mace on 2018/7/17 9:14.
     * @param request
     * @return: java.lang.String
     */
    public static String getIpAddr(HttpServletRequest request){

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
