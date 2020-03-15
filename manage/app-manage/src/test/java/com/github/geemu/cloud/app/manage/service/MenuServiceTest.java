package com.github.geemu.cloud.app.manage.service;

import com.github.geemu.cloud.app.manage.BaseTest;
import com.github.geemu.cloud.model.manage.entity.MenuEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 资源操作单元测试
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-09 20:18
 */
@Slf4j
public class MenuServiceTest extends BaseTest {

    @Autowired
    private MenuService menuService;

    @Test
    public void findAll() {
        List<MenuEntity> list = menuService.findAll();
        log.info("结果为:{}", list);
    }

}