package com.prisma.securityInterface.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "UserInfo", description = "Stores Data of a User will Signing Up")
public class AuthRequestDTO {
    private String username;
    private String password;
}
