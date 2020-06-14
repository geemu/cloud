package com.github.geemu.cloud.manage.app.controller;

import com.github.geemu.cloud.base.entity.BaseResponse;
import com.github.geemu.cloud.manage.app.config.annotation.CurrentUser;
import com.github.geemu.cloud.manage.app.config.log.SysLog;
import com.github.geemu.cloud.manage.app.config.log.SysModule;
import com.github.geemu.cloud.manage.app.config.log.SysOperation;
import com.github.geemu.cloud.manage.app.config.security.UserDetail;
import com.github.geemu.cloud.manage.app.converter.UserConverter;
import com.github.geemu.cloud.manage.app.service.UserService;
import com.github.geemu.cloud.manage.domain.request.UserOperation;
import com.github.geemu.cloud.manage.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户管理
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-15 18:16
 */
@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("sys/user")
@Api(tags = {"系统管理", "用户管理"})
@ApiResponses({@ApiResponse(code = 200000, message = "成功", response = BaseResponse.class)})
public class UserController {

    private final UserService userService;

    private final SessionRegistry sessionRegistry;

    private final UserConverter userConverter;

    @SysLog(module = SysModule.USER_MANAGE, operation = SysOperation.INSERT, value = "新增用户")
    @ApiOperation("新增")
    @PostMapping
    public BaseResponse<Long> post(@Valid UserOperation userOperation, @CurrentUser UserDetail user) {
        UserEntity userEntity = userConverter.userController_Post_In(userOperation);
        return new BaseResponse<>(userService.add(userEntity, user));
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

    @ApiOperation("在线用户")
    @GetMapping("online")
    public BaseResponse<List<Object>> get() {
        List<Object> list = sessionRegistry.getAllPrincipals();
        return new BaseResponse<>(list);
    }

}
