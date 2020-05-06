package com.github.geemu.cloud.manage.app.config;

import com.github.geemu.cloud.manage.app.config.annotation.CurrentUser;
import com.github.geemu.cloud.manage.app.config.security.SecurityUtils;
import com.github.geemu.cloud.manage.app.config.security.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 绑定当前用户
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-17 20:11
 */
@Slf4j
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 当前解析器是否支持当前参数的解析
     * @param parameter 要检查的方法参数
     * @return {@code true} 如果此解析器支持提供的参数|{@code false} 其它情况
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter
                .getParameterType().isAssignableFrom(UserDetail.class)
                &&
                parameter.hasParameterAnnotation(CurrentUser.class);
    }

    /**
     * 解析参数
     * @param parameter parameter
     * @param mavContainer mavContainer
     * @param webRequest webRequest
     * @param binderFactory binderFactory
     * @return Object
     * @throws Exception Exception
     */
    @Nullable
    @Override
    public Object resolveArgument(@Nullable MethodParameter parameter, ModelAndViewContainer mavContainer, @Nullable NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return SecurityUtils.getCurrentUser();
    }

}
