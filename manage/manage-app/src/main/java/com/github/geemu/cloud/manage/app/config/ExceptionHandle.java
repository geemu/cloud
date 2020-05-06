package com.github.geemu.cloud.manage.app.config;

import com.github.geemu.cloud.base.entity.BaseResponse;
import com.github.geemu.cloud.base.entity.BaseResponseState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-17 21:27
 */
@Slf4j
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class ExceptionHandle {

    /**
     * 请求路径不存在
     * @param request HttpServletRequest
     * @return JSON视图
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponse<Void> handleNoHandlerFoundException(HttpServletRequest request) {
        log.warn("请求路径{}:{}不存在", request.getMethod(), request.getRequestURI());
        return new BaseResponse<>(BaseResponseState.NOF_FOUND_404000);
    }

    /**
     * 绑定异常
     * @param ex BindException
     * @param request HttpServletRequest
     * @return JSON视图
     */
    @ExceptionHandler(BindException.class)
    public BaseResponse<Void> handleBindException(BindException ex, HttpServletRequest request) {
        log.warn("参数绑定异常,请求路径:{}:{}{},异常:", request.getMethod(), request.getRequestURI(), null == request.getQueryString() ? "" : request.getQueryString(), ex);
        return new BaseResponse<>(BaseResponseState.BAD_REQUEST_400004);
    }

    /**
     * 未知异常
     * @param ex 异常
     * @param request HttpServletRequest
     * @return JSON视图
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<Void> handleException(Exception ex, HttpServletRequest request) {
        log.error("后台未知异常,请求路径:{}:{}{},异常:", request.getMethod(), request.getRequestURI(), null == request.getQueryString() ? "" : request.getQueryString(), ex);
        return new BaseResponse<>(BaseResponseState.INTERNAL_SERVER_ERROR_500000);
    }

}
