package com.github.geemu.cloud.app.manage.config.test;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * {PACKAGE_NAME}
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-15 12:22
 */
@Slf4j
@Data
public class MyAuthenticationProvider implements AuthenticationProvider {

    PasswordEncoder passwordEncoder;

    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authentication.getName());
        MyUsernamePasswordAuthToken dd = new MyUsernamePasswordAuthToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
        dd.setAuthenticated(true);
        return dd;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return (MyUsernamePasswordAuthToken.class.isAssignableFrom(authentication));
    }

}
