package com.github.geemu.cloud.manage.app.config.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validator;

/**
 * Spring扩展的 支持方法参数校验的配置
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-12-29 11:33
 */
@Slf4j
@Configuration
public class MethodValidationPostProcessorConfig {

    @Autowired
    private Validator validator;

    /**
     * Spring validator支持对方法的校验
     * @return MethodValidationPostProcessor
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        log.info("初始化:MethodValidationPostProcessor");
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(validator);
        return postProcessor;
    }
}
