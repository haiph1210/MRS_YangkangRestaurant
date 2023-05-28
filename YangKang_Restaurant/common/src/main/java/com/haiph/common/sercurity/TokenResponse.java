package com.haiph.common.sercurity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class TokenResponse {
    private String token;
    private UserReponse user;
}
