package com.prisma.securityInterface.Models;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserInfo", description = "Stores Data of a User will Signing Up")
public class UserInfoDTO  {

    private String username;
    private String password;

    private Long phoneNumber;

    private String email;
}
