package com.mace.microservice.oauth2_token.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * description:
 * <br />
 * Created by mace on 11:41 2018/7/20.
 */
@ConfigurationProperties(prefix = "mace.security.jwt")
@PropertySource("classpath:jwt-auth-config.properties")
@Component
@Getter
@ToString
public class JwtAuthenticationConfig {

    private String url="/login";

    private String header="Authorization";

    private String prefix="mace";

    // default 24 hours
    private int expiration = 60*15;

    private String secret="";
}
