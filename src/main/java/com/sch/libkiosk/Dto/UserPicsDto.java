package com.sch.libkiosk.Dto;

import com.sch.libkiosk.Entity.User;
import com.sch.libkiosk.Entity.UserPics;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UserPicsDto {

    private String picUrl;

    private String picName;
    private User user;

    private List<MultipartFile> fileList;

    public UserPics toEntity(){
        return UserPics.builder()
                .picPath(picUrl)
                .picName(picName)
                .user(user)
                .build();
    }

    @Builder
    public UserPicsDto(User user, String picUrl, List<MultipartFile> fileList){
        this.user = user;
        this.picUrl = picUrl;
        this.fileList = fileList;
    }
}
