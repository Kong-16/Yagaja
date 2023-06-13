package com.ssafy.enjoytrip.auth.jwt;

import com.ssafy.enjoytrip.user.model.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;

public class JwtAuthentication implements Authentication {

    public static class JwtAuthenticationDetails implements Serializable {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(final String token) {
            this.token = token;
        }
    }

    private String username;
    private String principal;
    private boolean isAuthenticated;
    private JwtAuthenticationDetails details = new JwtAuthenticationDetails();
    private EnumSet<UserRole> authorities = EnumSet.noneOf(UserRole.class);

    public String getToken() {
        return details.getToken();
    }

    public void setToken(final String token) {
        details.setToken(token);
    }

    @Override
    public String getName() {
        return username;
    }

    public void setName(final String name) {
        this.username = name;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public void setPrincipal(final String principal) {
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return getToken();
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void addAuthority(final UserRole role) {
        authorities.add(role);
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

}
