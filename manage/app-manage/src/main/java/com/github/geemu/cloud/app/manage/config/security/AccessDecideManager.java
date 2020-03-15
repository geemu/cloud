package com.github.geemu.cloud.app.manage.config.security;

import com.github.geemu.cloud.base.entity.BaseResponseState;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;


/**
 * 判断用户是否有权限
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-12-01 18:55
 */
@Data
@Slf4j
@Builder
@Component
public class AccessDecideManager implements AccessDecisionManager {

    /**
     * 判断用户是否有权限访问资源
     * @param auth 认证对象
     * @param obj 被保护的对象
     * @param col 当前资源所能访问的角色列表
     * @throws AccessDeniedException AccessDeniedException
     * @throws InsufficientAuthenticationException InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication auth, Object obj, Collection<ConfigAttribute> col) throws AccessDeniedException, InsufficientAuthenticationException {
        UserDetail currentUser = SecurityUtils.getCurrentUser(auth);
        if (null == currentUser) {
            log.warn("未认证用户拒绝访问受保护资源");
            throw new InsufficientAuthenticationException(BaseResponseState.AUTHENTICATION_000.getMessage());
        }
        // 当前资源角色列表
        Iterator<ConfigAttribute> menuRoleList = col.iterator();
        // 当前用户角色列表
        Collection<? extends GrantedAuthority> currentUserRoleList = auth.getAuthorities();
        while (menuRoleList.hasNext()) {
            // 当前资源角色
            String menRole = menuRoleList.next().getAttribute();
            for (GrantedAuthority currentUserRole : currentUserRoleList) {
                if (currentUserRole.getAuthority().equals(menRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException(BaseResponseState.AUTHORIZATION_403000.getMessage());
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return Boolean.TRUE;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Boolean.TRUE;
    }

}
