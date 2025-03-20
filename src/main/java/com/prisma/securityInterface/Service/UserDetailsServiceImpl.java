package com.prisma.securityInterface.Service;

import com.prisma.securityInterface.Entity.UserInfo;
import com.prisma.securityInterface.Models.UserInfoDTO;
import com.prisma.securityInterface.Repositry.UserRepositry;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepositry userRepositry;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepositry.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("Could not found user...!!");
        }
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfoDTO userInfoDTO){
        return  userRepositry.findByUsername(userInfoDTO.getUsername());
    }

    public Boolean signUp(UserInfoDTO userInfoDTO){

        //Define to check if the userEmail and phone is Correct
       userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
       if(Objects.nonNull(checkIfUserAlreadyExists(userInfoDTO))){
           return false;
       }
       String userId= UUID.randomUUID().toString();
       UserInfo userInfo=new UserInfo();
       userInfo.setUserId(userId);
       userInfo.setUsername(userInfoDTO.getUsername());
       userInfo.setPassword(userInfoDTO.getPassword());
       userInfo.setUserRoles(new HashSet<>());
       userRepositry.save(userInfo);
       return true;
    }
}
