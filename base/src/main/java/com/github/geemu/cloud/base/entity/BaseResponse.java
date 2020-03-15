package com.github.geemu.cloud.base.entity;

import com.github.geemu.cloud.base.exception.BizException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 基础返回体
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 11:39
 */
@Slf4j
@Getter
@ToString
@EqualsAndHashCode
public class BaseResponse<T> {

    /** 返回状态码 **/
    private Integer code;
    /** 返回提示信息 **/
    private String message;
    /** 返回数据 **/
    private T data;

    /**
     * 自定义code、message、data
     * @param code code
     * @param message message
     * @param data data
     */
    public BaseResponse(ResponseState code, String message, T data) {
        this(code.getCode(), message, data);
    }

    /**
     * 构造
     * @param code 返回状态码
     * @param message 返回提示信息
     * @param data 返回数据
     */
    private BaseResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 自定义code、message、data
     * @param state code、message
     * @param data data
     */
    public BaseResponse(ResponseState state, T data) {
        this(state.getCode(), state.getMessage(), data);
    }

    /**
     * 自定义code、message
     * @param code code
     * @param message message
     */
    public BaseResponse(ResponseState code, String message) {
        this(code.getCode(), code.getMessage(), null);
    }

    /**
     * 自定义code、message
     * @param state code、message
     */
    public BaseResponse(ResponseState state) {
        this(state.getCode(), state.getMessage(), null);
    }

    /**
     * 自定义data
     * @param data data
     */
    public BaseResponse(T data) {
        this(BaseResponseState.SUCCESS_200000.getCode(), BaseResponseState.SUCCESS_200000.getMessage(), data);
    }

    /**
     * 成功
     */
    public BaseResponse() {
        this(BaseResponseState.SUCCESS_200000.getCode(), BaseResponseState.SUCCESS_200000.getMessage(), null);
    }

    /**
     * 自定义code、message
     * @param ex code、message
     */
    @SuppressWarnings("unchecked")
    public BaseResponse(BizException ex) {
        this(ex.getCode(), ex.getMessage(), (T) ex.getData());
    }


}
