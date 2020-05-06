package com.github.geemu.cloud.manage.entity.view;

import com.github.geemu.cloud.manage.entity.RoleEntity;
import com.github.geemu.cloud.manage.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 一个角色对应多个用户
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-06-16 21:45
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("一个角色对应多个用户")
public class RoleUserView extends RoleEntity {

    @ApiModelProperty("角色对应的用户集合")
    private List<UserEntity> userEntityList;

}
