package com.github.geemu.cloud.app.manage.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-12-01 16:24
 */
@Slf4j
@RestController
@RequestMapping("test")
@Api(tags = "测试")
public class TestController {

    @GetMapping("test")
    public String test(String data) {
        log.info("{}", data);
        return data + "8766";
    }

}
