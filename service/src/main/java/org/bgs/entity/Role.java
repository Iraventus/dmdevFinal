package org.bgs.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    MANAGER,
    CUSTOMER;

    @Override
    public String getAuthority() {
        return name();
    }
}
