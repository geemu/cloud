package com.github.geemu.cloud.app.manage.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geemu.cloud.base.entity.BaseResponse;
import com.github.geemu.cloud.base.entity.BaseResponseState;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 处理器
 * haor
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-10 23:03
 */
@Data
@Slf4j
@Builder
@Component
public class Handler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, AccessDeniedHandler, AuthenticationEntryPoint, LogoutSuccessHandler {

    /** 序列化 **/
    @Autowired private ObjectMapper objectMapper;

    /**
     * 未登录用户拒绝访问受保护资源处理器
     * @param httpReq HttpServletRequest
     * @param httpResp HttpServletResponse
     * @param e AuthenticationException
     * @throws IOException IOException
     */
    @Override
    public void commence(HttpServletRequest httpReq, HttpServletResponse httpResp, AuthenticationException e) throws IOException {
        writeJson(httpResp, BaseResponseState.AUTHENTICATION_000, objectMapper);
    }

    /**
     * 输出json
     * @param httpResp httpResp
     * @param baseResponseState baseResponseState
     * @throws IOException IOException
     */
    private static void writeJson(HttpServletResponse httpResp, BaseResponseState baseResponseState, ObjectMapper objectMapper) throws IOException {
        if (httpResp.isCommitted()) {
            return;
        }
        httpResp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        String json = objectMapper.writeValueAsString(new BaseResponse<>(baseResponseState));
        httpResp.getWriter().print(json);
        httpResp.getWriter().flush();
        httpResp.getWriter().close();
    }

    /**
     * 登录成功处理器
     * @param httpReq HttpServletRequest
     * @param httpResp HttpServletResponse
     * @param e AuthenticationException
     * @throws IOException IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpReq, HttpServletResponse httpResp, Authentication e) throws IOException {
        HttpSession session = httpReq.getSession(Boolean.FALSE);
        if (null != session) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        writeJson(httpResp, BaseResponseState.SUCCESS_200000, objectMapper);
    }

    /**
     * 登录失败处理器
     * @param httpReq HttpServletRequest
     * @param httpResp HttpServletResponse
     * @param e AuthenticationException
     * @throws IOException IOException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpReq, HttpServletResponse httpResp, AuthenticationException e) throws IOException {
        writeJson(httpResp, BaseResponseState.AUTHENTICATION_004, objectMapper);
    }

    /**
     * 认证用户拒绝访问无权限资源处理器
     * @param httpReq HttpServletRequest
     * @param httpResp HttpServletResponse
     * @param e AccessDeniedException
     * @throws IOException IOException
     */
    @Override
    public void handle(HttpServletRequest httpReq, HttpServletResponse httpResp, AccessDeniedException e) throws IOException {
        if (httpResp.isCommitted()) {
            return;
        }
        UserDetail currentUser = SecurityUtils.getCurrentUser();
        log.warn("鉴权不通过,用户:[{}],试图访问无权限资源:[{}]", null != currentUser ? currentUser.getUsername() : null, httpReq.getMethod() + ":" + httpReq.getRequestURI());
        writeJson(httpResp, BaseResponseState.AUTHORIZATION_403000, objectMapper);
    }

    /**
     * 注销成功处理器
     * @param httpReq HttpServletRequest
     * @param httpResp HttpServletResponse
     * @param e Authentication
     * @throws IOException IOException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpReq, HttpServletResponse httpResp, Authentication e) throws IOException {
        writeJson(httpResp, BaseResponseState.SUCCESS_200000, objectMapper);
    }

}
