package com.sch.libkiosk.Entity;

import com.sch.libkiosk.Entity.Enums.Sex;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userBirth;

    @Column(nullable = false)
    private String userPhoneNum;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    //얼굴인식 동의 여부
    @Column(nullable = true)
    private Boolean frAgree;

    //회원 카드 rfid 번호
    @Column(nullable = true)
    private Long rfidNum;


    public void updateUser(String userPhoneNum, String loginId, String password, Boolean frAgree){
        this.userPhoneNum = userPhoneNum;
        this.loginId = loginId;
        this.password = password;
        this.frAgree = frAgree;
    }

    public void updateUserCard(Long rfidNum){
        this.rfidNum = rfidNum;
    }

    @Builder
    public User(String userName, String userBirth, String userPhoneNum, String loginId, String password, Sex sex, Boolean frAgree, Long rfidNum){
        this.userName = userName;
        this.userBirth = userBirth;
        this.userPhoneNum = userPhoneNum;
        this.loginId = loginId;
        this.password = password;
        this.sex = sex;
        this.frAgree = frAgree;
        this.rfidNum = rfidNum;
    }
}
