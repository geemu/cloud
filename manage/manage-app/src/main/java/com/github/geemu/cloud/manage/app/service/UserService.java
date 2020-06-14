package com.github.geemu.cloud.manage.app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.geemu.cloud.base.entity.BaseResponseState;
import com.github.geemu.cloud.base.exception.BizException;
import com.github.geemu.cloud.manage.app.config.security.UserDetail;
import com.github.geemu.cloud.manage.entity.UserEntity;
import com.github.geemu.cloud.manage.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 用户操作
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-08 19:55
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

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

    /**
     * 新增一名用户
     * @param userEntity 用户
     * @param user 用户
     * @return 用户id主键
     */
    public Long add(UserEntity userEntity, UserDetail user) {
        if (exist(userEntity.getUsername())) {
            throw new BizException(BaseResponseState.BAD_REQUEST_400002);
        }
        LocalDateTime now = LocalDateTime.now();
        String password = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("随机密码为:{}", password);
        UserEntity entity = UserEntity
                .builder()
                .username(userEntity.getUsername())
                .password(passwordEncoder.encode(password))
                .remark(userEntity.getRemark())
                .enabled(userEntity.getEnabled())
                .createUser(user.getUsername())
                .createTime(now)
                .updateUser(user.getUsername())
                .updateTime(now)
                .build();
        int id = userMapper.insert(entity);
        return (long) id;
    }

    /**
     * 用户是否存在
     * @param username 用户名
     * @return {@code true}存在、{@code false}不存在
     */
    public boolean exist(String username) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        UserEntity userEntity = UserEntity
                .builder()
                .username(username)
                .build();
        queryWrapper.setEntity(userEntity);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 删除用户
     * @param userId 用户id
     * @return {@code true}成功、{@code false}失败
     */
    public boolean delete(long userId) {
        if (!exist(userId)) {
            throw new BizException(BaseResponseState.BAD_REQUEST_400003);
        }
        return userMapper.deleteById(userId) > 0;
    }

    /**
     * 用户是否存在
     * @param userId 用户id
     * @return {@code true}存在、{@code false}不存在
     */
    public boolean exist(long userId) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        UserEntity userEntity = UserEntity
                .builder()
                .userId(userId)
                .build();
        queryWrapper.setEntity(userEntity);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 根据用户id,查询用户
     * @param userId 用户id
     * @return UserEntity
     */
    public UserEntity findByUserId(Long userId) {
        return userMapper.selectById(userId);
    }

}
