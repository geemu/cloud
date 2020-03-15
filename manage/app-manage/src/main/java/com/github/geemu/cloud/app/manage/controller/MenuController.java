package com.github.geemu.cloud.app.manage.controller;

import com.github.geemu.cloud.base.entity.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源管理
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-15 18:16
 */
@Slf4j
@RestController
@RequestMapping("sys/menu")
@Api(tags = {"系统管理", "资源管理"})
public class MenuController {

    @ApiOperation("新增")
    @PostMapping
    public BaseResponse<Long> post() {
        return new BaseResponse<>(10L);
    }

    @ApiOperation("删除")
    @DeleteMapping
    public BaseResponse<Void> delete() {
        return new BaseResponse<>();
    }

    @ApiOperation("修改")
    @PutMapping
    public BaseResponse<Void> put() {
        return new BaseResponse<>();
    }

    @ApiOperation("查询")
    @GetMapping
    public BaseResponse<String> get() {
        return new BaseResponse<>("这是资源信息");
    }

}
