package com.github.geemu.cloud.manage.app.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisTemplate配置
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-04-23 21:26
 */
@Slf4j
@Configuration
public class RedisTemplateConfig {

    /**
     * 自定义序列化模板
     * 序列化时带上参数类型
     * @param connectionFactory connectionFactory
     * @param string string
     * @param json json
     */
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, StringRedisSerializer string, Jackson2JsonRedisSerializer<Object> json) {
        log.info("初始化:RedisTemplate");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setDefaultSerializer(json);
        redisTemplate.setKeySerializer(string);
        redisTemplate.setValueSerializer(json);
        redisTemplate.setHashKeySerializer(string);
        redisTemplate.setHashValueSerializer(json);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


}
