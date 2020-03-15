package com.github.geemu.cloud.app.manage.service;

import com.github.geemu.cloud.app.manage.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户角色关联操作单元测试
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-09 20:18
 */
@Slf4j
public class UserRoleServiceTest extends BaseTest {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    public void findByUserId() {
        List<Long> list = userRoleService.findRoleIdsByUserId(321321L);
        log.info("结果为:{}", list);
    }

}