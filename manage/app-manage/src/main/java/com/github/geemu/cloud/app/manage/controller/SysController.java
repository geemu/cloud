package com.github.geemu.cloud.app.manage.controller;

import com.github.geemu.cloud.base.entity.BaseResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-13 23:57
 */
@Slf4j
@RestController
@Api(tags = "系统管理")
public class SysController {

    @PostMapping("user")
    public BaseResponse<Long> post() {
        return new BaseResponse<>(10L);
    }

    @DeleteMapping("user")
    public BaseResponse<Void> delete() {
        return new BaseResponse<>();
    }

    @PutMapping("user")
    public BaseResponse<Void> put() {
        return new BaseResponse<>();
    }

    @GetMapping("user")
    public BaseResponse<String> get() {
        return new BaseResponse<>("这是用户信息");
    }

}
