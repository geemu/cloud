package com.github.geemu.cloud.manage.app.config.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-05-07 20:47
 */
@Getter
@AllArgsConstructor
public enum SysOperation {

    INSERT("新增"),
    DELETE("删除"),
    UPDATE("修改"),
    SELECT("查询"),
    IMPORT("导入"),
    EXPORT("导出");

    /** 操作 **/
    private final String operation;

}
