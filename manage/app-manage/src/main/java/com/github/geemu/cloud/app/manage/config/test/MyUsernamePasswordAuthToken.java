//package com.github.geemu.cloud.app.manage.config.test;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
///**
// * {PACKAGE_NAME}
// * @author 陈方明  cfmmail@sina.com
// * @since 2020-03-15 12:24
// */
//public class MyUsernamePasswordAuthToken extends UsernamePasswordAuthenticationToken {
//
//    @JsonIgnore
//    private Object details;
//    private boolean authenticated = false;
//
//    public MyUsernamePasswordAuthToken(Object principal, Object credentials) {
//        super(principal, credentials);
//    }
//
//    public MyUsernamePasswordAuthToken() {
//        super(null, null);
//    }
//
//    /**
//     * This constructor should only be used by <code>AuthenticationManager</code> or
//     * <code>AuthenticationProvider</code> implementations that are satisfied with
//     * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
//     * authentication token.
//     */
//    public MyUsernamePasswordAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
//        super(principal, credentials, authorities);
//    }
//
//    public void setAuthenticated(boolean isAuthenticated) {
//        this.authenticated = isAuthenticated;
//    }
//
//}
