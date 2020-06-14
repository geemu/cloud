package com.github.geemu.cloud.manage.app.config.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模块
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-05-07 20:47
 */
@Getter
@AllArgsConstructor
public enum SysModule {

    USER_MANAGE("用户管理");

    /** 模块名称 **/
    private final String name;

}
