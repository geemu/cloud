//package com.github.geemu.cloud.app.manage.test;
//
//
//import com.github.geemu.cloud.app.manage.config.security.Handler;
//import com.github.geemu.cloud.app.manage.config.security.ObjectPostHandler;
//import com.github.geemu.cloud.app.manage.service.RoleMenuService;
//import com.github.geemu.cloud.app.manage.service.RoleService;
//import com.github.geemu.cloud.app.manage.service.UserRoleService;
//import com.github.geemu.cloud.app.manage.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.util.Arrays;
//
///**
// * 安全配置
// * @author 陈方明  cfmmail@sina.com
// * @since 2018-11-23 15:00
// */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfigTest extends WebSecurityConfigurerAdapter {
//
//    /** 用户服务 **/
//    @Autowired private UserService userService;
//    /** 角色服务 **/
//    @Autowired private RoleService roleService;
//    /** 用户角色服务 **/
//    @Autowired private UserRoleService userRoleService;
//    /** 角色权限服务 **/
//    @Autowired private RoleMenuService roleMenuService;
//    /** 自定义加载用户 **/
//    @Autowired private UserDetailsService userDetailsService;
//    /** 异常处理 **/
//    @Autowired private Handler handler;
//    /** 资源加载 **/
//    @Autowired private FilterInvocationSecurityMetadataSource metadataSource;
//    /** 权限校验 **/
//    @Autowired private AccessDecisionManager accessDecisionManager;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .eraseCredentials(Boolean.TRUE)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Override
//    public void configure(WebSecurity webSecurity) {
//        webSecurity
//                .ignoring()
//                .mvcMatchers(HttpMethod.GET, "/static/**", "/favicon.ico");
//
//    }
//
//    /**
//     * 具体的权限控制规则配置
//     */
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.cors().disable();
//        httpSecurity.csrf().disable();
//        httpSecurity.httpBasic().disable();
//        httpSecurity.anonymous().disable();
//        httpSecurity.requestCache().disable();
//        httpSecurity.headers().xssProtection().disable();
//        httpSecurity.headers().cacheControl().disable();
//        httpSecurity.headers().httpStrictTransportSecurity().disable();
//        httpSecurity.headers().frameOptions().disable();
//        httpSecurity.headers().httpPublicKeyPinning().disable();
//        httpSecurity.exceptionHandling().accessDeniedHandler(handler).authenticationEntryPoint(handler);
//        httpSecurity.logout().clearAuthentication(Boolean.TRUE).logoutSuccessHandler(handler);
//        httpSecurity
//                .authorizeRequests()
//                .withObjectPostProcessor(ObjectPostHandler
//                        .builder()
//                        .metadataSource(metadataSource)
//                        .accessDecisionManager(accessDecisionManager)
//                        .build())
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().successHandler(handler).failureHandler(handler);
//        httpSecurity.addFilterAt(myUsernamePasswordAuthFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        //                .formLogin().disable();
//        //        httpSecurity.formLogin().loginProcessingUrl("/login");
//        //                .and()
//        //                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//    }
//
//    @Bean
//    public MyAuthenticationProvider myAuthenticationProvider() {
//        MyAuthenticationProvider bean = new MyAuthenticationProvider();
//        bean.setUserDetailsService(userDetailsService());
//        bean.setPasswordEncoder(passwordEncoder());
//        return bean;
//    }
//
//    @Bean
//    public MyUsernamePasswordAuthFilter myUsernamePasswordAuthFilter() throws Exception {
//        MyUsernamePasswordAuthFilter bean = new MyUsernamePasswordAuthFilter();
//        ProviderManager aaaaaa = new ProviderManager(Arrays.asList(myAuthenticationProvider()));
//        bean.setAuthenticationManager(aaaaaa);
//        return bean;
//    }
//
//    @Primary
//    @Bean("springSessionDefaultRedisSerializer")
//    public RedisSerializer<Object> setDefaultRedisSerializer(Jackson2JsonRedisSerializer<Object> json) {
//        return json;
//    }
//
//}
