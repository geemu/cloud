package com.github.geemu.cloud.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.geemu.cloud.manage.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色数据表操作
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-06-12 22:56
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<RoleEntity> {

}
