package com.github.geemu.cloud.app.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.geemu.cloud.base.CollectionUtils;
import com.github.geemu.cloud.mapper.manage.RoleMapper;
import com.github.geemu.cloud.model.manage.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 角色操作
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-08 20:51
 */
@Slf4j
@Service
@AllArgsConstructor
public class RoleService {

    private RoleMapper roleMapper;

    /**
     * 根据角色id列表,查询有效角色列表
     * @param roleIds 资角色id列表
     * @return 有效角色列表
     */
    public List<RoleEntity> findByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        RoleEntity entity = RoleEntity
                .builder()
                .enabled(Boolean.TRUE)
                .build();
        QueryWrapper<RoleEntity> query = new QueryWrapper<>();
        query.setEntity(entity);
        query.in("role_id", roleIds);
        return roleMapper.selectList(query);
    }

    /**
     * 查询有效角色列表
     * @return 有效角色列表
     */
    public List<RoleEntity> findAll() {
        RoleEntity entity = RoleEntity
                .builder()
                .enabled(Boolean.TRUE)
                .build();
        QueryWrapper<RoleEntity> query = new QueryWrapper<>();
        query.setEntity(entity);
        return roleMapper.selectList(query);
    }

}
