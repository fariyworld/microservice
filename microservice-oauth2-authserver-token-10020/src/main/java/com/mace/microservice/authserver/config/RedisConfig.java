package com.mace.microservice.authserver.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * description:
 * <br />
 * Created by mace on 14:35 2018/7/20.
 */
@Configuration
@EnableCaching//启用缓存,这个注解很重要
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 缓存管理器.
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {

        CacheManager cacheManager = new RedisCacheManager(redisTemplate);

        return cacheManager;
    }

    /**
     * redis模板操作类,类似于jdbcTemplate的一个类;
     * <br />
     * 解决redis自动生成key，key乱码，为自定义key
     * <br />
     * @param factory 通过Spring进行注入，参数在application.yml进行配置；
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(factory);

//        RedisConnectionFactory redisConnectionFactory = factory;
//        JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) redisConnectionFactory;
//        log.info("hostname: "+jedisConnectionFactory.getHostName());
//        log.info("use pool: "+jedisConnectionFactory.getUsePool());
//        log.info("password: "+jedisConnectionFactory.getPassword());
//        log.info("port: "+jedisConnectionFactory.getPort());
//        log.info("datebase: "+jedisConnectionFactory.getDatabase());
//        log.info("timeout: "+jedisConnectionFactory.getTimeout());
//        JedisPoolConfig poolConfig = jedisConnectionFactory.getPoolConfig();
//        log.info("max-idle: "+poolConfig.getMaxIdle());
//        log.info("max-wait: "+poolConfig.getMaxWaitMillis());
//        log.info("min-idle: "+poolConfig.getMinIdle());

        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
