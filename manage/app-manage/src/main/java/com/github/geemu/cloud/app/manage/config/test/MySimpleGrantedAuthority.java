package com.github.geemu.cloud.app.manage.config.test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * {PACKAGE_NAME}
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-15 13:12
 */
public class MySimpleGrantedAuthority implements GrantedAuthority {


    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String authority;

    public MySimpleGrantedAuthority(String authority) {
        this.authority = authority;
    }

    public MySimpleGrantedAuthority() {

    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public int hashCode() {
        return this.authority.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof MySimpleGrantedAuthority) {
            return authority.equals(((MySimpleGrantedAuthority) obj).authority);
        }

        return false;
    }

    @Override
    public String toString() {
        return this.authority;
    }

}
