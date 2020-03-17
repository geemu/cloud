package com.github.geemu.cloud.base.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 返回状态码、返回提示信息
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-11-16 14:54
 */
@Getter
@ToString
@AllArgsConstructor
public enum BaseResponseState implements ResponseState {

    /** 通用成功 **/
    SUCCESS_200000(200000, "成功"),


    /** 客户端请求参数缺失 **/
    BAD_REQUEST_400000(400000, "客户端请求参数缺失"),
    /** 客户端请求参数校验不通过 **/
    BAD_REQUEST_400001(400001, "客户端请求参数校验不通过"),
    /** 用户已存在 **/
    BAD_REQUEST_400002(400002, "用户已存在"),
    /** 用户不存在 **/
    BAD_REQUEST_400003(400003, "用户不存在"),


    /** 未认证 **/
    AUTHENTICATION_000(401000, "未认证"),
    /** 认证已过期 **/
    AUTHENTICATION_001(401001, "认证已过期"),
    /** 用户名错误 **/
    AUTHENTICATION_002(401002, "用户名错误"),
    /** 密码错误 **/
    AUTHENTICATION_003(401003, "密码错误"),
    /** 用户名或密码错误 **/
    AUTHENTICATION_004(401004, "用户名或密码错误"),
    /** 账户被禁用 **/
    AUTHENTICATION_005(401005, "账户被禁用"),


    /** 未授权 **/
    AUTHORIZATION_403000(403000, "权限不足"),


    /** 请求路径不存在 **/
    NOF_FOUND_404000(404000, "请求路径不存在"),


    /** 通用服务端异常 **/
    INTERNAL_SERVER_ERROR_500000(500000, "服务器未知异常");

    /** 返回状态码 **/
    private Integer code;
    /** 返回提示信息 **/
    private String message;

}
