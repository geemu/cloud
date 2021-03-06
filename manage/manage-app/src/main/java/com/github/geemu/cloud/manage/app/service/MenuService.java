package com.github.geemu.cloud.manage.app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.geemu.cloud.base.CollectionUtils;
import com.github.geemu.cloud.manage.entity.MenuEntity;
import com.github.geemu.cloud.manage.mapper.MenuMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 资源操作
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-08 20:43
 */
@Slf4j
@Service
@AllArgsConstructor
public class MenuService {

    private MenuMapper menuMapper;

    /**
     * 根据资源id列表,查询有效资源列表
     * @param menuIds 资源id列表
     * @return 有效的资源列表
     */
    public List<MenuEntity> findByIds(List<Long> menuIds) {
        if (CollectionUtils.isEmpty(menuIds)) {
            return Collections.emptyList();
        }
        MenuEntity entity = MenuEntity
                .builder()
                .enabled(Boolean.TRUE)
                .build();
        QueryWrapper<MenuEntity> query = new QueryWrapper<>();
        query.setEntity(entity);
        query.in("menu_id", menuIds);
        return menuMapper.selectList(query);
    }

    /**
     * 查询所有有效资源列表
     * @return 所有有效资源列表
     */
    public List<MenuEntity> findAll() {
        MenuEntity entity = MenuEntity
                .builder()
                .enabled(Boolean.TRUE)
                .build();
        QueryWrapper<MenuEntity> query = new QueryWrapper<>();
        query.setEntity(entity);
        query.orderByAsc("sort");
        return menuMapper.selectList(new QueryWrapper<>());
    }

}
