package com.haiph.authservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haiph.authservice.feignclient.UserController;
import com.haiph.authservice.request.AuthRequest;
import com.haiph.authservice.request.UserChangePassword;
import com.haiph.authservice.request.UserRequest;
import com.haiph.common.dto.response.APIResponse;
import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.common.sercurity.TokenResponse;
import com.haiph.common.sercurity.UserInfo;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;

import java.io.IOException;
import java.util.LinkedHashMap;

@Service
public class AuthServiceImpl implements com.haiph.authservice.service.AuthService {
    @Autowired
    private UserController userController;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public TokenResponse login(AuthRequest request) {
        APIResponse<TokenResponse> response = userController.RESPONSE(AuthRequest.build(request.getUsername(), request.getPassword()));
        if (response != null) {
            TokenResponse tokenResponse = response.getResponseData();
            UserInfo userInfo = UserInfo.
                    build(tokenResponse.getToken(),
                            tokenResponse.getUser());
//            save token to Redis
            redisTemplate.opsForValue().set(tokenResponse.getToken(), userInfo);
//            SecurityContextHolder.getContext().setAuthentication(userInfo);
            return tokenResponse;
        } else if (response.getResponseData() == null) {
            throw new CommonException(Response.ACCESS_DENIED, "Login Fail");
        }
        return null;
    }

    @Override
    public String register(UserRequest request) {
        APIResponse<String> response;
        try {
            response = userController.register(request);
            System.out.println("response = " + response);
            if (response != null) {
                String responseToClient = response.getResponseData();
                System.out.println("responseToClient = " + responseToClient);
                return responseToClient;
            }
        } catch (FeignException.InternalServerError e) {
            String responseBody = e.contentUTF8();
            ObjectMapper mapper = new ObjectMapper();
            Object responseData = "";
            try {
                ResponseBody errorResponse = mapper.readValue(responseBody, ResponseBody.class);
                String responseCode = errorResponse.getResponseCode();
                String responseMessage = errorResponse.getResponseMessage();
                responseData =  errorResponse.getResponseData();
                // Sử dụng thông tin từ phản hồi
                System.out.println("responseCode = " + responseCode);
                System.out.println("responseMessage = " + responseMessage);
                System.out.println("responseData = " + responseData);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw new CommonException(Response.NOT_FOUND, (String) responseData);
        }
        throw new CommonException(Response.PARAM_INVALID, "Đăng ký thất bại");

    }

    @Override
    public String changePassword(UserChangePassword request) {
        APIResponse<String> response = userController.changePassword(request);
        if (response != null) {
            String responseToClient = response.getResponseData();
            return responseToClient;
        } else if (response.getResponseData() == null) {
            throw new CommonException(Response.PARAM_INVALID, "Register Fail");
        }
        return null;
    }

    public UserInfo userInfo(String token) {
//        getToken use Token
        Object result = redisTemplate.opsForValue().get(token);
        if (result instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) result;
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(map, UserInfo.class);
        }
        return null;
    }

}
