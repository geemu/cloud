package com.github.geemu.cloud.app.manage.controller;

import com.github.geemu.cloud.app.manage.config.annotation.CurrentUser;
import com.github.geemu.cloud.app.manage.config.security.UserDetail;
import com.github.geemu.cloud.app.manage.service.UserService;
import com.github.geemu.cloud.base.entity.BaseResponse;
import com.github.geemu.cloud.model.manage.entity.UserEntity;
import com.github.geemu.cloud.model.manage.request.UserAdd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-15 18:16
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("sys/user")
@Api(tags = {"系统管理", "用户管理"})
public class UserController {

    private UserService userService;

    @ApiOperation("新增")
    @PostMapping
    public BaseResponse<Long> post(@CurrentUser UserDetail user, UserAdd add) {
        return new BaseResponse<>(userService.add(add.getUsername(), add.getRemark(), add.getEnabled(), user.getUsername()));
    }

    @ApiOperation("删除")
    @DeleteMapping
    public BaseResponse<Boolean> delete(Long userId) {
        return new BaseResponse<>(userService.delete(userId));
    }

    @ApiOperation("修改")
    @PutMapping
    public BaseResponse<Void> put() {
        return new BaseResponse<>();
    }

    @ApiOperation("查询")
    @GetMapping("{userId}")
    public BaseResponse<UserEntity> get(@PathVariable("userId") Long userId) {
        return new BaseResponse<>(userService.findByUserId(userId));
    }

}
