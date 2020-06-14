package com.github.geemu.cloud.manage.app.config.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-05-06 22:54
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {

    /** 模块 **/
    SysModule module();

    /** 操作 **/
    SysOperation operation();

    /** 描述 **/
    String value();

}
