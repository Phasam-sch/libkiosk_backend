package com.sch.libkiosk.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserPics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String picPath;

    @Column(nullable = false)
    private String picName;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @Builder
    public UserPics(String picPath,String picName, User user){
        this.picPath = picPath;
        this.picName = picName;
        this.user = user;
    }
}
