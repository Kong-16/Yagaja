package com.ssafy.enjoytrip.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Setter
public class UserJoinDto {

    private String id;

    private String password;
    private String name;

    private String emailId;

    private String emailDomain;
}
