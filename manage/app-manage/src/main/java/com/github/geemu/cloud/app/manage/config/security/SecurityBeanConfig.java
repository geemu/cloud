package com.github.geemu.cloud.app.manage.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 其他Bean配置
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-17 20:39
 */
@Slf4j
@Configuration
public class SecurityBeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("初始化:PasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        log.info("初始化:SessionRegistry");
        return new SessionRegistryImpl();
    }

}
