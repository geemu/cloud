package com.github.geemu.cloud.app.manage.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {PACKAGE_NAME}
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-13 23:29
 */
@Slf4j
@RestController
@RequestMapping("login")
@Api(tags = "测试")
public class LoginController {

    @PostMapping
    public String test(String username, String password) {
        return "dsaa";
    }

}
