package com.sch.libkiosk.Dto;

import com.sch.libkiosk.Entity.Enums.Sex;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class SignupDto {
    private String userName;

    private String userBirth;

    private String userPhoneNum;

    private String password;

    private Sex sex;

    private Boolean frAgree;

//    private List<MultipartFile> fileList;

}
