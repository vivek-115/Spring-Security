package com.prisma.securityInterface.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    //Storing Tokens, checking whether tokens are Expired ore not

    public static final String SECRET="a3433609a2bb480113f87187791d5d0dd2d60e7a2186e2b893200bc2c4783abd93106edd6b088ddfce2982de60ea005800d950597584f7c1900ea79aab405c31240f0e8e79b83d29f0d2e2293caf0991659704465cb48a57ad426456c259f5ec7830acf1ee3c0aeeb4856107de32199377c18c150b5dcd088ffd297385dd2fc34d13bfb3de76caa01173f6d1ec51e15cdf5a72aae1d8bf06c410e889c356f489fb4d65cff0c60e3bfd3c43b6816c73e7e6fde7fe2eeaa805aed889b74a2f703d03c56c2f3650533f271fe802d1cf054171e21068af2171d359b548f4f4cc047f3960fb5bd721a2dc319d39cfccc6c60962d87c42dbb1f0abd0815bf72619dc13";


    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);  //from subject we can extract the username
    }

    public <T>T extractClaim(String token, Function<Claims,T >claimResolver){
        final Claims claims=extractAllClaims(token);
         return claimResolver.apply(claims);
    }

public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);

}

private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
}

public Boolean validateToken(String token, UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
}

    public String GenerateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

private String createToken(Map<String , Object> claims, String username){
        return Jwts.builder()
                .setClaims(claims)
                .subject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60+1))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
}

    private Claims extractAllClaims(String token){
        return Jwts
                  .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



    private Key getSignKey(){
            byte[] keyBytes= Decoders.BASE64.decode(SECRET);
            return Keys.hmacShaKeyFor(keyBytes);
    }

}
