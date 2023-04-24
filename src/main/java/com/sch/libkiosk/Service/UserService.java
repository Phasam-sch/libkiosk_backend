package com.sch.libkiosk.Service;

import com.sch.libkiosk.Dto.SignupDto;
import com.sch.libkiosk.Dto.UserDto;
import com.sch.libkiosk.Entity.User;
import com.sch.libkiosk.Repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long SignUp(UserDto userDto){
        //user 정보를 DB에 저장후, ID를 가져옴.
        return userRepository.save(userDto.toEntity()).getId();
    }

    @Transactional
    public List<UserDto> getAllUser(){
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for(User u : userList){
            UserDto userDto = UserDto.builder()
                    .userName(u.getUserName())
                    .userBirth(u.getUserBirth())
                    .userPhoneNum(u.getUserPhoneNum())
                    .password(u.getPassword())
                    .sex(u.getSex())
                    .frAgree(u.getFrAgree())
                    .build();

            userDtoList.add(userDto);
        }

        return userDtoList;
    }

    @Transactional
    public User getUserEntity(Long uid){
        Optional<User> optionalUser = userRepository.findById(uid);
        if(optionalUser.isEmpty()){
            throw new EntityExistsException();
        }
        return optionalUser.get();
    }

//    @Transactional
//    public User updateUser

//    @Transactional
//    public void DeleteUser()
}
