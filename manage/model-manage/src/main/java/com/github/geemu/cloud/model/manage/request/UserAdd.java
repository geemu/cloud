package com.github.geemu.cloud.model.manage.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户新增
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-17 20:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel("用户新增")
public class UserAdd {

    @ApiModelProperty("用户名  忽略大小写")
    private String username;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("是否启用 true:启用、false:禁用")
    private Boolean enabled;

}
