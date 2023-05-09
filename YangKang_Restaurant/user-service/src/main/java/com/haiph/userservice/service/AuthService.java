package com.haiph.userservice.service;

import com.haiph.userservice.dto.request.UserRequest;
import com.haiph.userservice.dto.request.sercurity.LoginRequest;
import com.haiph.userservice.dto.response.sercurity.TokenRespone;
import org.springframework.security.core.AuthenticationException;

public interface AuthService {
    TokenRespone login(LoginRequest request) throws AuthenticationException;

    String register(UserRequest request);

    String generateToken(String username);

    void validateToken(String token);
}
