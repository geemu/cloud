//package com.github.geemu.cloud.app.manage.config.security;
//
//import com.github.geemu.cloud.app.manage.BaseTest;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * {PACKAGE_NAME}
// * @author 陈方明  cfmmail@sina.com
// * @since 2020-03-12 21:19
// */
//@Slf4j
//public class WebSecurityConfigTest extends BaseTest {
//
//    @Autowired private PasswordEncoder passwordEncoder;
//
//    @Test
//    public void bCryptPasswordEncoder() {
//        String text = "123456";
//        log.info("加密后的密码为:{}", passwordEncoder.encode(text));
//    }
//
//}