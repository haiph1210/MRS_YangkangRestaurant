package com.haiph.authservice.feignclient;

import com.haiph.authservice.request.AuthRequest;
import com.haiph.authservice.request.UserChangePassword;
import com.haiph.authservice.request.UserRequest;
import com.haiph.common.dto.response.APIResponse;
import com.haiph.common.sercurity.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "user-service")
public interface UserController {
    @PostMapping("/api/auth/login")
    APIResponse<TokenResponse> RESPONSE(@RequestBody AuthRequest request);
    @PostMapping("/api/auth/register")
    APIResponse<String> register(@RequestBody UserRequest request);
    @PutMapping("/api/auth/change-password")
    APIResponse<String> changePassword(@RequestBody UserChangePassword request);
}
