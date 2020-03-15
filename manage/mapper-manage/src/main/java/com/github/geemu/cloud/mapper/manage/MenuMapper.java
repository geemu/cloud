package com.github.geemu.cloud.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.geemu.cloud.model.manage.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 资源数据表操作
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-06-12 22:56
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<MenuEntity> {

}
