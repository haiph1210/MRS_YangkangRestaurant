package com.haiph.userservice.service;

import com.haiph.userservice.dto.request.UserRequest;
import com.haiph.userservice.dto.request.sercurity.LoginRequest;
import com.haiph.userservice.dto.response.sercurity.TokenRespone;

public interface AuthService {
    TokenRespone login(LoginRequest request);

    String register(UserRequest request);

    String generateToken(String username);

    void validateToken(String token);
}
