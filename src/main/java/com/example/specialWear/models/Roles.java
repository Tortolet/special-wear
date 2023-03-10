package com.example.specialWear.models;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_USER,
    ROLE_EMPLOYEE,
    ROLE_OPERATOR,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
