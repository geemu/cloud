package com.github.geemu.cloud.app.manage.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * CustomUserDetails
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-01-01 14:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail implements UserDetails, Serializable {

    /** 序列化id **/
    private static final long serialVersionUID = -1L;

    /** 用户id **/
    private Long userId;
    /** 用户明 **/
    private String username;
    /** 密码 **/
    @JsonIgnore private String password;
    @JsonIgnore private boolean credentialsNonExpired;
    @JsonIgnore private boolean accountNonLocked;
    @JsonIgnore private boolean accountNonExpired;
    /** 是否启用 true:启用、false:禁用 **/
    private Boolean isEnabled;
    /** 角色id集合 **/
    private ArrayList<MySimpleGrantedAuthority> authorities;

    @Override
    public ArrayList<MySimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the password used to authenticate the user.
     * @return the password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

}
