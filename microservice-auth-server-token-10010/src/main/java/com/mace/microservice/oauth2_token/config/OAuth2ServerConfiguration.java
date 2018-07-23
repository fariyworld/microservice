package com.mace.microservice.oauth2_token.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mace.microservice.oauth2_token.client.RedisClientDetailsService;
import com.mace.microservice.oauth2_token.code.RedisAuthorizationCodeServices;
import com.mace.microservice.oauth2_token.token.store.RedisTemplateTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * description: 开启授权服务器
 * <br />
 * Created by mace on 11:24 2018/7/20.
 */
@Configuration
@EnableAuthorizationServer
//@AutoConfigureAfter(AuthorizationServerEndpointsConfigurer.class)
public class OAuth2ServerConfiguration extends AuthorizationServerConfigurerAdapter {

    /**
     * description: 注入authenticationManager 来支持 password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * springmvc启动时自动装配json处理类
     */
    @Resource
    private ObjectMapper objectMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired(required = false)
    private RedisTemplateTokenStore redisTokenStore;

    @Autowired(required = false)
    private JwtTokenStore jwtTokenStore;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    @Resource
    private DataSource dataSource;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;


//    @Autowired
//    private RedisClientDetailsService clientDetailsService;

    @Bean // 声明 ClientDetails实现
    @ConditionalOnProperty(prefix = "security.oauth2.token.store", name = "type", havingValue = "redis", matchIfMissing = true)
    public RedisClientDetailsService clientDetailsService() {
        RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
        clientDetailsService.setRedisTemplate(redisTemplate);
        return clientDetailsService;
    }

    @Bean
    public RandomValueAuthorizationCodeServices authorizationCodeServices() {
        RedisAuthorizationCodeServices redisAuthorizationCodeServices = new RedisAuthorizationCodeServices();
        redisAuthorizationCodeServices.setRedisTemplate(redisTemplate);
        return redisAuthorizationCodeServices;
    }

    /**
     * description: 配置身份认证器，配置认证方式
     * <br /><br />
     * create by mace on 2018/7/20 15:46.
     * @param endpoints
     * @return: void
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        super.configure(endpoints);

        if (jwtTokenStore != null) {
            endpoints
                        .tokenStore(jwtTokenStore)
                        .authenticationManager(authenticationManager)
                        .userDetailsService(userDetailsService); // 支持
            // password
            // grant
            // type;
        } else if (redisTokenStore != null) {
            endpoints
                        .tokenStore(redisTokenStore)
                        .authenticationManager(authenticationManager)
                        .userDetailsService(userDetailsService); // 支持
            // password
            // grant
            // type;
        }

        if (jwtAccessTokenConverter != null) {
            endpoints.accessTokenConverter(jwtAccessTokenConverter);
        }

        endpoints.authorizationCodeServices(authorizationCodeServices());
        endpoints.exceptionTranslator(webResponseExceptionTranslator);
    }

    /**
     * description: 配置应用名称 应用id
     *              配置OAuth2的客户端相关信息
     * <br /><br />
     * create by mace on 2018/7/20 15:48.
     * @param clients
     * @return: void
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        super.configure(clients);
        RedisClientDetailsService clientDetailsService = clientDetailsService();
        clients.withClientDetails(clientDetailsService);
        clientDetailsService.loadAllClientToCache();
    }


    /**
     * description: 对应于配置AuthorizationServer安全认证的相关信息，
     *              创建ClientCredentialsTokenEndpointFilter核心过滤器
     * <br /><br />
     * create by mace on 2018/7/20 15:51.
     * @param security
     * @return: void
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        super.configure(security);
        security
                    .tokenKeyAccess("permitAll()") /// url:/oauth/token_key,exposes
                    /// public key for token
                    /// verification if using
                    /// JWT tokens
                    .checkTokenAccess("isAuthenticated()") // url:/oauth/check_token
                    // allow check token
                    .allowFormAuthenticationForClients();

    }
}
