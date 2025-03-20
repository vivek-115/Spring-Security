package com.prisma.securityInterface.Repositry;

import com.prisma.securityInterface.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositry extends JpaRepository<UserInfo, String> {

   UserInfo findByUsername(String username);

}
