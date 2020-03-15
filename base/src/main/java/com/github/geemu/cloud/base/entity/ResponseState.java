package com.github.geemu.cloud.base.entity;

/**
 * 返回状态
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-10-27 10:21
 */
public interface ResponseState {

    /**
     * 获取状态码
     * @return 状态码
     */
    Integer getCode();

    /**
     * 获取状态提示信息
     * @return 状态提示信息
     */
    String getMessage();

}
