package com.sch.libkiosk.Controller;

import com.sch.libkiosk.Dto.LoginDto;
import com.sch.libkiosk.Dto.TokenDto;
import com.sch.libkiosk.Entity.User;
import com.sch.libkiosk.Security.JwtFilter;
import com.sch.libkiosk.Security.TokenProvider;
import com.sch.libkiosk.Service.CamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final CamService camService;
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getLoginId(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/authenticate/fr")
    public ResponseEntity<TokenDto> FRauthroize(
            @RequestParam("pics") List<MultipartFile> pics
    ){
        try{
            User user = camService.uploadLoginPic(pics);
            //토큰 발급 시도
            LoginDto loginDto = LoginDto.builder()
                    .loginId(user.getLoginId())
                    .password(user.getPassword())
                    .build();

            return authorize(loginDto);

        }catch(Exception e){
            log.error("존재하지 않는 사용자입니다.");
            return ResponseEntity.badRequest().build();
        }
    }
}
