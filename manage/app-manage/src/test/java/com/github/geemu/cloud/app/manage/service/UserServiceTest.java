package com.github.geemu.cloud.app.manage.service;

import com.github.geemu.cloud.app.manage.BaseTest;
import com.github.geemu.cloud.model.manage.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户操作单元测试
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-09 19:18
 */
@Slf4j
public class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void findByUsername() {
        UserEntity userEntity = userService.findByUsername("管理员用户");
        log.info("结果为:{}", userEntity);
        Assert.assertNotNull(userEntity);
    }

}