package com.github.geemu.cloud.manage.entity.view;

import com.github.geemu.cloud.manage.entity.MenuEntity;
import com.github.geemu.cloud.manage.entity.RoleEntity;
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
 * 一个资源对应多个角色
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-06-16 18:52
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("一个资源对应多个角色")
public class MenuRoleView extends MenuEntity {

    @ApiModelProperty("资源对应的角色集合")
    private List<RoleEntity> roleList;

}
