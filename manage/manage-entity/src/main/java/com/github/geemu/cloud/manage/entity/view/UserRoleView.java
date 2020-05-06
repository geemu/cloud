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
 * 一个用户对应多个角色
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-06-16 21:43
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("一个用户对应多个角色")
public class UserRoleView extends UserEntity {

    @ApiModelProperty("用户对应的角色集合")
    private List<RoleEntity> roleEntityList;

}
