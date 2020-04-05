package com.github.geemu.cloud.app.manage.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

/**
 * Redis Session配置
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-15 11:10
 */
@Slf4j
@EnableRedisHttpSession
public class RedisSessionConfig {

    @Bean
    public HeaderHttpSessionIdResolver headerHttpSessionIdResolver() {
        log.info("初始化:HeaderHttpSessionIdResolver");
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

}
