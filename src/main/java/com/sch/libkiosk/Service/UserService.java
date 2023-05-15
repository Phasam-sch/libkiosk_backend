package com.sch.libkiosk.Service;

import com.sch.libkiosk.Dto.UserDto;
import com.sch.libkiosk.Entity.User;
import com.sch.libkiosk.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void SignUp(UserDto userDto){
        userRepository.save(userDto.toEntity());
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
            user.updateUser(userDto.getUserPhoneNum(), userDto.getPassword(), userDto.getFrAgree());
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
