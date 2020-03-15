package com.github.geemu.cloud.app.manage.config.security;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * 自定义处理
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-13 21:32
 */
@Data
@Slf4j
@Builder
public class ObjectPostHandler implements ObjectPostProcessor<FilterSecurityInterceptor> {


    /** 资源加载 **/
    private FilterInvocationSecurityMetadataSource metadataSource;
    /** 权限校验 **/
    private AccessDecisionManager accessDecisionManager;

    /**
     * 初始化
     * @param o 待初始化的对象
     * @return 初始化后的对象
     */
    @Override
    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
        o.setSecurityMetadataSource(metadataSource);
        o.setAccessDecisionManager(accessDecisionManager);
        return o;
    }

}
