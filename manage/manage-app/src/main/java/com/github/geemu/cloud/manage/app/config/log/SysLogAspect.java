package com.github.geemu.cloud.manage.app.config.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
@Order(-5)
public class SysLogAspect {

//    @Pointcut("@annotation(com.github.geemu.cloud.manage.app.config.log.SysLog)")
//    public void logPointCut() {
//    }

    @Pointcut("execution(* com.github.geemu.cloud.manage.app.controller..*.*(..))")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {

        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

    }

}
