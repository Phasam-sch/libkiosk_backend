package com.sch.libkiosk.Service;

import com.sch.libkiosk.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String loginId){
       return userRepository.findOneWithAuthoritiesByLoginId(loginId)
               .map(user -> createUser(loginId, user))
               .orElseThrow(() -> new UsernameNotFoundException(loginId + " DB에 존재하지 않는 유저"));
    }

    private User createUser(String loginId, com.sch.libkiosk.Entity.User user){
        if(!user.isActivated()){
            throw new RuntimeException(loginId + " -> 활성화되어있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorityList = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new User(user.getLoginId(),
                user.getPassword(),
                grantedAuthorityList);
    }
}
