package com.sch.libkiosk.Controller;

import com.sch.libkiosk.Dto.SignupDto;
import com.sch.libkiosk.Dto.UserDto;
import com.sch.libkiosk.Entity.User;
import com.sch.libkiosk.Service.UserPicsService;
import com.sch.libkiosk.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserPicsService userPicsService;

    @PostMapping
    public Long signUp(@RequestBody SignupDto signupDto){
        Long ret = -1L;
        UserDto userDto = UserDto.builder()
                .userName(signupDto.getUserName())
                .userBirth(signupDto.getUserBirth())
                .userPhoneNum(signupDto.getUserPhoneNum())
                .password(signupDto.getPassword())
                .sex(signupDto.getSex())
                .frAgree(signupDto.getFrAgree())
                .build();

        try{
             ret = userService.SignUp(userDto);

        }catch(Exception e){
            log.error("Sign up Error ======\n" + signupDto + "\n\n");
            ret = -1L;
        }

        return ret;
    }

    @PostMapping("/pics")
    public ResponseEntity<?> postUserPics(
            @RequestParam("uid") Long uid,
            @RequestParam("pics") List<MultipartFile> pics) {
        Long userId;
        //존재하는 user 임을 확인
        if (userPicsService.isExist(uid)){
              try{
                  userPicsService.uploadUserPic(uid, pics);
              }catch(IOException e){
                    log.error(e.getMessage());
              }
        }else{
            log.error("User Not Exist");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getUserList(){
        List<UserDto> userDtoList = userService.getAllUser();
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/get/{id}")
    public UserDto getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    //PUT은 요청된 객체로 통쨰로 수정(교체)
    //PATCH는 일부 데이터만 수정
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        try {
            userService.updateUser(userDto, id);
        }catch(EntityNotFoundException e){
            log.error("User not Exist");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    
    
    @PatchMapping("/card/{id}")
    public ResponseEntity<?> updateUserCard(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        try{
            userService.updateUserCard(userDto, id);
        }catch(EntityNotFoundException e){
            log.error("User not Exist");
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    //로그인
    //안면인식 관련
    //사진 업데이트

}
