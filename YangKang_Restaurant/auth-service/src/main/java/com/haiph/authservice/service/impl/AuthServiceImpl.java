package com.haiph.authservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haiph.authservice.feignclient.UserController;
import com.haiph.authservice.request.AuthRequest;
import com.haiph.common.dto.response.APIResponse;
import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.common.sercurity.TokenResponse;
import com.haiph.common.sercurity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class AuthServiceImpl {
    @Autowired
    private UserController userController;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public TokenResponse login(AuthRequest request) {
        APIResponse<TokenResponse> response = userController.RESPONSE(AuthRequest.build(request.getUsername(), request.getPassword()));
        if (response != null) {
            TokenResponse tokenResponse = response.getResponseData();
            UserInfo userInfo = UserInfo.
                    build(tokenResponse.getToken(),
                            tokenResponse.getUsername(),
                            tokenResponse.getUserCode(),
                            tokenResponse.getUser().getEmail());
            redisTemplate.opsForValue().set("UserInfo", userInfo);
            return tokenResponse;
        } else if (response.getResponseData() == null) {
            throw new CommonException(Response.ACCESS_DENIED,"Login Fail");
        }
        return null;
    }
    private UserInfo userInfo() {
        Object result = redisTemplate.opsForValue().get("UserInfo");
        if (result instanceof LinkedHashMap<?,?>) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) result;
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(map, UserInfo.class);
        }
        return null;
    }

    public UserInfo getUserInfo() {
        UserInfo userInfo  = userInfo();
        if (userInfo != null) {
            return userInfo;
        }return null;
    }
}
