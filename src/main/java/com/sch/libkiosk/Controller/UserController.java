package com.sch.libkiosk.Controller;

import com.sch.libkiosk.Dto.SignupDto;
import com.sch.libkiosk.Dto.UserDto;
import com.sch.libkiosk.Dto.UserPicsDto;
import com.sch.libkiosk.Service.UserPicsService;
import com.sch.libkiosk.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserPicsService userPicsService;

    @PostMapping
    public Long signUp(@RequestBody SignupDto signupDto){

        UserDto userDto = UserDto.builder()
                .userName(signupDto.getUserName())
                .userBirth(signupDto.getUserBirth())
                .userPhoneNum(signupDto.getUserPhoneNum())
                .password(signupDto.getPassword())
                .sex(signupDto.getSex())
                .frAgree(signupDto.getFrAgree())
                .build();

        return userService.SignUp(userDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getUserList(){
        List<UserDto> userDtoList = userService.getAllUser();
        return ResponseEntity.ok(userDtoList);
    }

    @PostMapping
    public Long signUp(
            @RequestParam("uid") Long uid,
            @RequestParam("pics") List<MultipartFile> pics
    ) throws Exception{
        //user 존재 유무 체크
        userService.

        //사진 저장

    }
}
