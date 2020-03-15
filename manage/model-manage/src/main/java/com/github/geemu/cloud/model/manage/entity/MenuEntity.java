package com.github.geemu.cloud.model.manage.entity;

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
 * 权限数据表
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-10-25 20:36
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel("权限数据表")
@TableName("backend_menu")
public class MenuEntity {

    @ApiModelProperty("权限id")
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;
    @ApiModelProperty("上级权限id 0是顶级id")
    @TableField("parent_id")
    private Long parentId;
    @ApiModelProperty("菜单名称  不区分大小写")
    @TableField("name")
    private String name;
    @ApiModelProperty("菜单类型  0目录  1菜单  2按钮")
    @TableField("type")
    private Byte type;
    @ApiModelProperty("请求方法 目录没有方法 菜单有方法 按钮有方法 0:POST、1:DELETE、2:PUT、3:GET")
    @TableField("method")
    private String method;
    @ApiModelProperty("请求路径 目录没有pattern 菜单是路径 按钮有pattern")
    @TableField("pattern")
    private String pattern;
    @ApiModelProperty("用于按钮的隐藏 0:新增、1:删除、2:修改、3:查询、4:启用禁用、4:导入、5:导出")
    @TableField("operate")
    private Byte operate;
    @ApiModelProperty("排序  数值越大越靠后")
    @TableField("sort")
    private Long sort;
    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
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
