package com.github.geemu.cloud.manage.app.config.log;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志切面配置
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-05-07 20:52
 */
@Configuration
public class SysLogConfig {

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor3() {
        SysLogInterceptor interceptor = new SysLogInterceptor();
        AnnotationMatchingPointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(SysLog.class);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }

}
