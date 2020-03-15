package com.github.geemu.cloud.app.manage.config.test;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {PACKAGE_NAME}
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-15 12:49
 */
public class MyUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (null == username) {
            username = "";
        }
        if (null == password) {
            password = "";
        }
        username = username.trim();
        MyUsernamePasswordAuthToken authRequest = new MyUsernamePasswordAuthToken(username, password);
        setDetails(request, authRequest);
        return super.getAuthenticationManager().authenticate(authRequest);
    }

}
