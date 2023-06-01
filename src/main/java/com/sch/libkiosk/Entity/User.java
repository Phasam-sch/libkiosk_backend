package com.sch.libkiosk.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sch.libkiosk.Entity.Enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userBirth;

    @Column(nullable = false)
    private String userPhoneNum;

    @Column(nullable = false, unique = true)
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

    @JsonIgnore
    @Column(nullable = true)
    private boolean activated;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "userAuthority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authorityName", referencedColumnName = "authorityName")})
    private Set<Authority> authorities;

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
    public User(String userName, String userBirth, String userPhoneNum, String loginId, String password, Sex sex, Boolean frAgree, Long rfidNum, Boolean activated, Set<Authority> authorities){
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
