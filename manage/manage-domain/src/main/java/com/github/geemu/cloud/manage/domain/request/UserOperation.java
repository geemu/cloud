package com.github.geemu.cloud.manage.domain.request;

import com.github.geemu.cloud.base.validate.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 用户操作实体
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
public class UserOperation {

    @Null(message = "主键必须为空", groups = {UpdateGroup.class})
    @NotNull(message = "用户名不能为空", payload = Uuuuu.class)
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名  忽略大小写")
    private String username;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("是否启用 true:启用、false:禁用")
    private Boolean enabled;

    static class Uuuuu implements Payload {

        private String aaaaa = "aa";

    }

}
