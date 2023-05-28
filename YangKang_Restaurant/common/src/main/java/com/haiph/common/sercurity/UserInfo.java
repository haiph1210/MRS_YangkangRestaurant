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
    private UserReponse userReponse;
}
