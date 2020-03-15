package com.github.geemu.cloud.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.geemu.cloud.model.manage.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关联数据表操作
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-06-12 22:55
 */
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

}
