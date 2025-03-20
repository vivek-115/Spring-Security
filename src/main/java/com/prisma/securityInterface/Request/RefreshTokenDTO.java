package com.prisma.securityInterface.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "RefreshToken", description = "Stores Refresh Token of a User ")
public class RefreshTokenDTO {
    private String token;
}
