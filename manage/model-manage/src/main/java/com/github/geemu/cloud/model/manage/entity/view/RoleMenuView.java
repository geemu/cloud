package com.github.geemu.cloud.model.manage.entity.view;

import com.github.geemu.cloud.model.manage.entity.MenuEntity;
import com.github.geemu.cloud.model.manage.entity.RoleEntity;
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
 * 一个角色对应多个资源
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-06-16 21:44
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("一个角色对应多个资源")
public class RoleMenuView extends RoleEntity {

    @ApiModelProperty("角色对应的资源集合")
    private List<MenuEntity> menuEntityList;

}
