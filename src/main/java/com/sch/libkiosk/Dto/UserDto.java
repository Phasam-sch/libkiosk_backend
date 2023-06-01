package com.sch.libkiosk.Dto;

import com.sch.libkiosk.Entity.Authority;
import com.sch.libkiosk.Entity.Enums.Sex;
import com.sch.libkiosk.Entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private String userName;

    private String userBirth;

    private String userPhoneNum;

    private String loginId;
    private String password;

    private Sex sex;

    private Boolean frAgree;

    private Long rfidNum;

    private Boolean activated;

    private Set<Authority> authorities;

    public User toEntity(){
        return User.builder()
                .userName(userName)
                .userBirth(userBirth)
                .userPhoneNum(userPhoneNum)
                .loginId(loginId)
                .password(password)
                .sex(sex)
                .frAgree(frAgree)
                .rfidNum(rfidNum)
                .activated(activated)
                .authorities(authorities)
                .build();
    }

    @Builder
    public UserDto(String userName, String userBirth, String userPhoneNum, String loginId, String password, Sex sex, Boolean frAgree, Long rfidNum, Boolean activated, Set<Authority> authorities){
        this.userName = userName;
        this.userBirth = userBirth;
        this.userPhoneNum = userPhoneNum;
        this.loginId = loginId;
        this.password = password;
        this.sex = sex;
        this.frAgree = frAgree;
        this.rfidNum = rfidNum;
        this.activated = activated;
        this.authorities = authorities;
    }
}
