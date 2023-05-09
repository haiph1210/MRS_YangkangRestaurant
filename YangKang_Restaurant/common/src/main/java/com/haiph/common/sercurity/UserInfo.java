package com.haiph.common.sercurity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
public class UserInfo {
    private String token;
    private String username;
    private String userCode;
    private String email;
    private String role;

    public UserInfo(String username, String userCode, String email) {
        this.username = username;
        this.userCode = userCode;
        this.email = email;
    }
}
