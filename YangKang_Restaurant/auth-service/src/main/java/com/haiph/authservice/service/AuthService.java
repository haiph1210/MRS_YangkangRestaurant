package com.haiph.authservice.service;

import com.haiph.authservice.request.AuthRequest;
import com.haiph.authservice.request.UserChangePassword;
import com.haiph.authservice.request.UserRequest;
import com.haiph.common.sercurity.TokenResponse;

public interface AuthService {
    TokenResponse login(AuthRequest request);

    String register(UserRequest request);

    String changePassword(UserChangePassword request);
}
