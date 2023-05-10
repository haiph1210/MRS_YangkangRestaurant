package com.haiph.userservice.dto.response.sercurity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class TokenRespone {
    private String token;
    private String username;
    private String userCode;
    private String role;
    private Object user;

}
