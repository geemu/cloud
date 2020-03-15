package com.github.geemu.cloud.app.manage.service;

import com.github.geemu.cloud.app.manage.BaseTest;
import com.github.geemu.cloud.model.manage.entity.view.MenuRoleView;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 角色资源关联操作单元测试
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-09 19:18
 */
@Slf4j
public class RoleMenuServiceTest extends BaseTest {

    @Autowired
    private RoleMenuService roleMenuService;

    @Test
    public void findAllWithRole() {
        List<MenuRoleView> list = roleMenuService.findAllWithRole();
        log.info("结果为:{}", list);
    }

}