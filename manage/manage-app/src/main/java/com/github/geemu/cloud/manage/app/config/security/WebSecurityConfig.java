package com.github.geemu.cloud.manage.app.config.security;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 安全配置
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-11-23 15:00
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /** 自定义加载用户 **/
    private UserDetailsService userDetailsService;
    /** 异常处理 **/
    private Handler handler;
    /** 资源加载 **/
    private FilterInvocationSecurityMetadataSource metadataSource;
    /** 权限校验 **/
    private AccessDecisionManager accessDecisionManager;
    /** 加密器 **/
    private PasswordEncoder passwordEncoder;
    /** SESSION管理 **/
    private SessionRegistry sessionRegistry;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .eraseCredentials(Boolean.TRUE)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity
                .ignoring()
                .mvcMatchers(HttpMethod.GET,
                        "/**.html",
                        "js/**",
                        "css/**",
                        "img/**",
                        "/favicon.ico",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/swagger/**",
                        "/v2/api-docs/**");

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();
        httpSecurity.httpBasic().disable();
        httpSecurity.anonymous().disable();
        httpSecurity.requestCache().disable();
        httpSecurity.headers().xssProtection().disable();
        httpSecurity.headers().cacheControl().disable();
        httpSecurity.headers().httpStrictTransportSecurity().disable();
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.headers().httpPublicKeyPinning().disable();
        httpSecurity.exceptionHandling().accessDeniedHandler(handler).authenticationEntryPoint(handler);
        httpSecurity.logout().clearAuthentication(Boolean.TRUE).logoutSuccessHandler(handler);
        httpSecurity.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry);
        httpSecurity
                .authorizeRequests()
                .withObjectPostProcessor(ObjectPostHandler
                        .builder()
                        .metadataSource(metadataSource)
                        .accessDecisionManager(accessDecisionManager)
                        .build())
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(handler).failureHandler(handler);

    }

}
