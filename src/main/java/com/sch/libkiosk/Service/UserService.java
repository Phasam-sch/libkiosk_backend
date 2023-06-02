package com.sch.libkiosk.Service;

import com.sch.libkiosk.Dto.UserDto;
import com.sch.libkiosk.Entity.Authority;
import com.sch.libkiosk.Entity.User;
import com.sch.libkiosk.Repository.UserRepository;
import com.sch.libkiosk.Security.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public User signUp(UserDto userDto){
        if (userRepository.findOneWithAuthoritiesByLoginId(userDto.getLoginId()).orElse(null) != null) {
            throw new RuntimeException("이미 가입된 사용자");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .userName(userDto.getUserName())
                .loginId(userDto.getLoginId())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .userBirth(userDto.getUserBirth())
                .userPhoneNum(userDto.getUserPhoneNum())
                .sex(userDto.getSex())
                .frAgree(userDto.getFrAgree())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String loginId) {
        return userRepository.findOneWithAuthoritiesByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentLoginId()
                .flatMap(userRepository::findOneWithAuthoritiesByLoginId);
    }

//    @Transactional
//    public List<UserDto> getAllUser(){
//        List<User> userList = userRepository.findAll();
//        List<UserDto> userDtoList = new ArrayList<>();
//
//        for(User u : userList){
//            UserDto userDto = UserDto.builder()
//                    .userName(u.getUserName())
////                    .userBirth(u.getUserBirth())
////                    .userPhoneNum(u.getUserPhoneNum())
////                    .password(u.getPassword())
////                    .sex(u.getSex())
//                    .fragree(u.getFrAgree())
//                    .build();
//
//            userDtoList.add(userDto);
//        }
//
//        return userDtoList;
//    }

    @Transactional
    public UserDto getUserById(Long uid) throws EntityNotFoundException{
        Optional<User> optionalUser = userRepository.findById(uid);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return  UserDto.builder()
                        .userName(user.getUserName())
                        .userBirth(user.getUserBirth())
                        .userPhoneNum(user.getUserPhoneNum())
                        .password(user.getPassword())
                        .sex(user.getSex())
                        .frAgree(user.getFrAgree())
                        .rfidNum(user.getRfidNum())
                        .build();
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public User getUserEntity(Long uid) throws EntityNotFoundException{
        Optional<User> optionalUser = userRepository.findById(uid);
        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException();
        }
        return optionalUser.get();
    }

    @Transactional
    public void updateUser(UserDto userDto, Long uid) throws EntityNotFoundException {
        Optional<User> optionalUser = userRepository.findById(uid);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.updateUser(userDto.getUserPhoneNum(), userDto.getLoginId(),userDto.getPassword(), userDto.getFrAgree());
            userRepository.save(user);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public void updateUserCard(UserDto userDto, Long uid) throws EntityNotFoundException {
        Optional<User> optionalUser = userRepository.findById(uid);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.updateUserCard(userDto.getRfidNum());
            userRepository.save(user);
        }else{
            throw new EntityNotFoundException();
        }
    }

//    @Transactional
//    public void DeleteUser()


}
