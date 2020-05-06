package com.github.geemu.cloud.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 用户角色关联数据表
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-10-25 20:35
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel("用户角色关联数据表")
@TableName("backend_user_role")
public class UserRoleEntity {

    @ApiModelProperty("主键  id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;
    @ApiModelProperty("角色id")
    @TableField("role_id")
    private Long roleId;
    @ApiModelProperty("是否启用 true:启用、false:禁用")
    @TableField("enabled")
    private Boolean enabled;
    @ApiModelProperty("创建人")
    @TableField("create_user")
    private String createUser;
    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;
    @ApiModelProperty("更新人")
    @TableField("update_user")
    private String updateUser;
    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

}
