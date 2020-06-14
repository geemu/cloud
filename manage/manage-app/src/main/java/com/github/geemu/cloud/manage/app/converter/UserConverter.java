package com.github.geemu.cloud.manage.app.converter;

import com.github.geemu.cloud.manage.domain.request.UserOperation;
import com.github.geemu.cloud.manage.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 对象映射
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-05-06 22:41
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    @Mappings({
            @Mapping(source = "username", target = "username"),
    })
    UserEntity userController_Post_In(UserOperation userOperation);

}
