package com.github.geemu.cloud.manage.app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.geemu.cloud.manage.entity.UserRoleEntity;
import com.github.geemu.cloud.manage.mapper.UserRoleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联操作
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-08 21:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserRoleService {

    private UserRoleMapper userRoleMapper;

    /**
     * 根据用户id,查询有效关联角色id列表
     * @param userId 用户id
     * @return 有效关联角色id列表
     */
    public List<Long> findRoleIdsByUserId(long userId) {
        UserRoleEntity entity = UserRoleEntity
                .builder()
                .userId(userId)
                .enabled(Boolean.TRUE)
                .build();
        QueryWrapper<UserRoleEntity> query = new QueryWrapper<>();
        query.setEntity(entity);
        List<UserRoleEntity> list = userRoleMapper.selectList(query);
        return list.stream()
                .map(UserRoleEntity::getRoleId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
