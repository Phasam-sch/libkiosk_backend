package com.sch.libkiosk.Controller;

import com.sch.libkiosk.Dto.SignupDto;
import com.sch.libkiosk.Dto.UserDto;
import com.sch.libkiosk.Entity.User;
import com.sch.libkiosk.Service.UserPicsService;
import com.sch.libkiosk.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserPicsService userPicsService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ){
        return ResponseEntity.ok(userService.signUp(userDto));
    }

    //회원권한 요청
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<User> getMyUserInfo(){
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    //회원 상세 정보
    @GetMapping("/user/{loginId}")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<User> getUserInfo(@PathVariable String loginId){
        return ResponseEntity.ok(userService.getUserWithAuthorities(loginId).get());
    }

    //회원 사진 저장
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

//    @GetMapping("/getAll")
//    public ResponseEntity<List<UserDto>> getUserList(){
//        List<UserDto> userDtoList = userService.getAllUser();
//        return ResponseEntity.ok(userDtoList);
//    }

//    @GetMapping("/get/{id}")
//    public UserDto getUserById(@PathVariable("id") Long id){
//        return userService.getUserById(id);
//    }

    //PUT은 요청된 객체로 통쨰로 수정(교체)
    //PATCH는 일부 데이터만 수정
//    @PatchMapping("/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto){
//        try {
//            userService.updateUser(userDto, id);
//        }catch(EntityNotFoundException e){
//            log.error("User not Exist");
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }
    
    
//    @PatchMapping("/card/{id}")
//    public ResponseEntity<?> updateUserCard(@PathVariable("id") Long id, @RequestBody UserDto userDto){
//        try{
//            userService.updateUserCard(userDto, id);
//        }catch(EntityNotFoundException e){
//            log.error("User not Exist");
//            return  ResponseEntity.internalServerError().build();
//        }
//        return ResponseEntity.ok().build();
//    }

    //안면인식 관련
    //사진 업데이트

}
