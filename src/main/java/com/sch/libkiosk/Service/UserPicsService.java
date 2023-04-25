package com.sch.libkiosk.Service;

import com.sch.libkiosk.Dto.PicUploadDto;
import com.sch.libkiosk.Dto.SignupDto;
import com.sch.libkiosk.Dto.UserPicsDto;
import com.sch.libkiosk.Entity.User;
import com.sch.libkiosk.Entity.UserPics;
import com.sch.libkiosk.Repository.UserPicsRepository;
import com.sch.libkiosk.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserPicsService {

    private final UserPicsRepository userPicsRepository;

    @Transactional
    public Boolean isExist(Long uid){
        try {
            userPicsRepository.findByUserId(uid);
        } catch (EntityNotFoundException E){
            log.error("User NotFound");
            return false;
        }
        return true;
    }
    @Transactional
    public Long uploadUserPic(UserPicsDto userPicsDto){

        //TODO:사진을 서버 특정 디렉토리에 저장 후, 해당 디렉토리 경로를 입력 후 저장 구현.
        //사진 저장
        return userPicsRepository.save(userPicsDto.toEntity()).getId();
    }

    @Transactional
    public UserPicsDto getUserPics(Long userId){
        UserPics up = userPicsRepository.findByUserId(userId);
        UserPicsDto userPicsDto = UserPicsDto.builder()
                .user(up.getUser())
                .picUrl(up.getPicPath())
                .build();

        return userPicsDto;
    }
}
