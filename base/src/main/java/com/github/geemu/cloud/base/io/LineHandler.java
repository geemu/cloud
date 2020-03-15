package com.github.geemu.cloud.base.io;

/**
 * 行处理器
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 11:50
 */
@FunctionalInterface
public interface LineHandler {

    /**
     * 处理一行数据，可以编辑后存入指定地方
     * @param line 行
     */
    void handle(String line);

}
