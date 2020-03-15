package com.github.geemu.cloud.app.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.geemu.cloud.mapper.manage.UserMapper;
import com.github.geemu.cloud.model.manage.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户操作
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-08 19:55
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private UserMapper userMapper;

    /**
     * 根据用户名,查询用户
     * @param username 用户名
     * @return UserEntity
     */
    public UserEntity findByUsername(String username) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        UserEntity userEntity = UserEntity
                .builder()
                .username(username)
                .build();
        queryWrapper.setEntity(userEntity);
        return userMapper.selectOne(queryWrapper);
    }

}
