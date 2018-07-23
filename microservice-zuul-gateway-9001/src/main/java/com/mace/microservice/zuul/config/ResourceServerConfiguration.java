package com.mace.microservice.zuul.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * description: 开启资源服务器
 *              配置需要token验证的资源
 * <br />
 * Created by mace on 14:14 2018/7/20.
 */
@Configuration
//@EnableResourceServer
@EnableOAuth2Sso//开启OAuth认证SSO
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {


}
