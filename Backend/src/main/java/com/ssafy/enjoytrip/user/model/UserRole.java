package com.ssafy.enjoytrip.user.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    USER("USER", 0), ADMIN("ADMIN", 1);

    private String roleName;
    private int roleIndex;

    private UserRole(final String roleName, final int roleIndex) {
        this.roleName = roleName;
        this.roleIndex = roleIndex;
    }

    public String getRoleName() {
        return roleName;
    }

    public int getRoleIndex() {
        return roleIndex;
    }

    public static UserRole of(final String roleName) throws IllegalArgumentException {
        switch (roleName) {
            case "USER":
                return USER;
            case "ADMIN":
                return ADMIN;
            default:
                // TODO(ME): write helpful exception message
                throw new IllegalArgumentException("");
        }
    }

    public static UserRole of(final int roleIndex) throws IllegalArgumentException {
        switch (roleIndex) {
            case 0:
                return USER;
            case 1:
                return ADMIN;
            default:
                // TODO(ME): write helpful exception message
                throw new IllegalArgumentException("");
        }
    }

    @Override
    public String toString() {
        return roleName;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + roleName;
    }

}
