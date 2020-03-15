package com.github.geemu.cloud.app.manage.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * SecurityUtils
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-03-30 11:10
 */
@Slf4j
public final class SecurityUtils {

    /**
     * 获取当前用户 如果为匿名用户返回null
     * @return UserDetail
     */
    public static UserDetail getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (null == context) {
            return null;
        }
        Authentication auth = context.getAuthentication();
        return getCurrentUser(auth);
    }

    /**
     * 获取当前用户 如果为匿名用户返回null
     * @return CustomUserDetails
     */
    public static UserDetail getCurrentUser(Authentication auth) {
        if (null == auth) {
            return null;
        }
        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        Object principal = auth.getPrincipal();
        if (null == principal) {
            return null;
        }
        return (UserDetail) principal;
    }

}
