package com.github.geemu.cloud.manage.app.config.validator;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Validator配置
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-12-29 11:32
 */
@Slf4j
@Configuration
public class ValidatorConfig {

    /**
     * Validator配置  配置快速失败
     * @return Validator Validator
     */
    @Bean
    public Validator validator() {
        log.info("初始化:Validator");
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
