package com.mace.microservice.common.annotation;

import java.lang.annotation.*;

/**
 * description: controller 日志注解
 * <br />
 * Created by mace on 15:23 2018/7/16.
 */
@Target({ElementType.TYPE, ElementType.METHOD})//该注解作用于类和方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableControllerLog {

    String description()  default "test"; //没有指定 defalut 的，需要在注解的时候显式指明
}
