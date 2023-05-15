package com.sch.libkiosk.Dto;

import com.sch.libkiosk.Entity.Enums.Sex;
import com.sch.libkiosk.Entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {

    private String userName;

    private String userBirth;

    private String userPhoneNum;

    private String password;

    private Sex sex;

    private Boolean frAgree;

    private Long rfidNum;

    public User toEntity(){
        return User.builder()
                .userName(userName)
                .userBirth(userBirth)
                .userPhoneNum(userPhoneNum)
                .password(password)
                .sex(sex)
                .frAgree(frAgree)
                .rfidNum(rfidNum)
                .build();
    }

    @Builder
    public UserDto(String userName, String userBirth, String userPhoneNum, String password, Sex sex, Boolean frAgree, Long rfidNum){
        this.userName = userName;
        this.userBirth = userBirth;
        this.userPhoneNum = userPhoneNum;
        this.password = password;
        this.sex = sex;
        this.frAgree = frAgree;
        this.rfidNum = rfidNum;
    }
}
