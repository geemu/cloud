package com.github.geemu.cloud.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.geemu.cloud.manage.entity.RoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色资源关联数据表操作
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-06-12 22:55
 */
@Mapper
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenuEntity> {

}
