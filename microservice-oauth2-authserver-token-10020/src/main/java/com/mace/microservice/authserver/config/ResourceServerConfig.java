package com.mace.microservice.authserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * description: 访问权限配置
 * <br />
 * Created by mace on 13:59 2018/7/23.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        // 关闭csrf
        http.csrf().disable();

        http
                .requestMatchers().antMatchers("/api/**")
                .and().authorizeRequests()
                .anyRequest().authenticated();

    }
}
