package com.github.geemu.cloud.app.manage.config.security;

import com.github.geemu.cloud.app.manage.service.RoleService;
import com.github.geemu.cloud.app.manage.service.UserRoleService;
import com.github.geemu.cloud.app.manage.service.UserService;
import com.github.geemu.cloud.base.StringUtils;
import com.github.geemu.cloud.model.manage.entity.RoleEntity;
import com.github.geemu.cloud.model.manage.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义加载用户数据
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-10 22:47
 */
@Data
@Slf4j
@Builder
@Primary
@Component
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {

    /** 用户操作 **/
    private UserService userService;
    /** 角色操作 **/
    private RoleService roleService;
    /** 用户角色关联操作 **/
    private UserRoleService userRoleService;

    /**
     * 据用户名查找用户
     * @param username 用户名
     * @return 用户
     * @throws UsernameNotFoundException 用户名不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用户:{}执行登录", username);
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        // 用户
        UserEntity userEntity = userService.findByUsername(username);
        if (null == userEntity) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 有效关联角色id列表
        List<Long> unionRoleIds = userRoleService.findRoleIdsByUserId(userEntity.getUserId());
        // 有效角色列表
        List<RoleEntity> roles = roleService.findByRoleIds(unionRoleIds);
        ArrayList<MySimpleGrantedAuthority> authority = roles.stream().map(e -> new MySimpleGrantedAuthority(e.getRoleId().toString())).collect(Collectors.toCollection(ArrayList::new));
        UserDetail data = new UserDetail();
        //        data.setUserId(userEntity.getUserId());
        //        data.setUsername(userEntity.getUsername());
        //        data.setPassword(userEntity.getPassword());
        //        data.setEnabled(userEntity.getEnabled());
        data.setUserId(userEntity.getUserId());
        data.setUsername(userEntity.getUsername());
        data.setPassword(userEntity.getPassword());
        data.setIsEnabled(userEntity.getEnabled());
        data.setAuthorities(authority);
        return data;
    }

}
