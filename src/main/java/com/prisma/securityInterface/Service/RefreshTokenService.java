package com.prisma.securityInterface.Service;

import com.prisma.securityInterface.Entity.RefreshToken;
import com.prisma.securityInterface.Entity.UserInfo;
import com.prisma.securityInterface.Repositry.RefreshTokenRepo;
import com.prisma.securityInterface.Repositry.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    private UserRepositry userRepositry;

    public RefreshToken createRefreshToken(String username){
       UserInfo userInfoExtracted= userRepositry.findByUsername(username);
     RefreshToken refreshToken=  RefreshToken.builder()
               .userInfo(userInfoExtracted)
               .token(UUID.randomUUID().toString())
               .expiryDate(Instant.now().plusMillis(600000))
               .build();

       return refreshTokenRepo.save(refreshToken);

    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(token);
            throw new RuntimeException(token.getToken()+" Refresh token is expired. Please login again..");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepo.findByToken(token);
    }

}
