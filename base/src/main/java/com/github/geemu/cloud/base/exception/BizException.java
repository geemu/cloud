package com.github.geemu.cloud.base.exception;

import com.github.geemu.cloud.base.entity.ResponseState;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-10-27 10:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {

    /** 返回状态码 **/
    private Integer code;
    /** 返回提示信息 **/
    private String message;
    /** 异常返回的数据 **/
    private Object data;

    /**
     * 自定义code、message、data
     * @param code code
     * @param message message
     * @param data data
     */
    public BizException(ResponseState code, String message, Object data) {
        this(code.getCode(), message, data);
    }

    /**
     * 构造
     * @param code 返回状态码
     * @param message 返回提示信息
     */
    private BizException(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 自定义code、message、data
     * @param state code、message
     * @param data data
     */
    public BizException(ResponseState state, Object data) {
        this(state.getCode(), state.getMessage(), data);
    }

    /**
     * 自定义code、message
     * @param code code
     */
    public BizException(ResponseState code, String message) {
        this(code.getCode(), message, null);
    }

    /**
     * 自定义code、message
     * @param state code、message
     */
    public BizException(ResponseState state) {
        this(state.getCode(), state.getMessage(), null);
    }

}
