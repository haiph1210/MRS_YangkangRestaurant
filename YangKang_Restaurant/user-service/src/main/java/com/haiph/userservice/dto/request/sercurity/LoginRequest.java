package com.haiph.userservice.dto.request.sercurity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "build")
public class LoginRequest {
    @NotNull(message = "username is not be null")
    private String username;
    @NotNull(message = "password is not be null")
    private String password;
}
