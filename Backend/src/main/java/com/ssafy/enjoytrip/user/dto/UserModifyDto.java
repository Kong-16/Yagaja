package com.ssafy.enjoytrip.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.enjoytrip.user.model.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserModifyDto {
    private String password;
    private String name;
    private String emailId;

    private String emailDomain;

    public void to(User user) {
        user.setName(this.name);
        user.setPassword(this.password);
        user.setEmailId(this.emailId);
        user.setEmailDomain(this.emailDomain);
    }
}
