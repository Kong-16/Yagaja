package com.ssafy.enjoytrip.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class User {
    @Id
    private String id;

    private String password;
    private String name;
    @Column(name = "email_id")
    private String emailId;

    @Column(name = "email_domain")
    private String emailDomain;
    @Builder
    public User(String id, String password, String name, String emailId, String emailDomain, int roleIndex) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.emailId = emailId;
        this.emailDomain = emailDomain;
        this.role = UserRole.of(roleIndex);
    }
    private UserRole role;
    public UserRole getRole() {
        return role;
    }

    public void setRole(final String roleName) {
        this.role = UserRole.of(roleName);
    }

    public void setRole(final int roleIndex) {
        this.role = UserRole.of(roleIndex);
    }

    @JsonIgnore
    public int getRoleIndex() {
        return role.getRoleIndex();
    }

    public void setRoleIndex(final int roleIndex) {
        this.role = UserRole.of(roleIndex);
    }

}
