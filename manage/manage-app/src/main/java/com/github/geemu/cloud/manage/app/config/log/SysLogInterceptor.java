package com.github.geemu.cloud.manage.app.config.log;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.PriorityOrdered;

import java.lang.reflect.Method;

/**
 * 日志切面
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-05-07 20:45
 */
@Slf4j
public class SysLogInterceptor implements MethodInterceptor, PriorityOrdered {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (null == sysLog) {
            return invocation.proceed();
        }
        log.info("before proceed");
        Object proceed = invocation.proceed();
        log.info("after proceed");
        return proceed;
    }

    @Override
    public int getOrder() {
        return -6;
    }
    
}
