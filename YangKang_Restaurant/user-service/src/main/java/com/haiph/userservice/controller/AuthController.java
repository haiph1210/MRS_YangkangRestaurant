package com.haiph.userservice.controller;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.userservice.dto.request.UserRequest;
import com.haiph.userservice.dto.request.sercurity.LoginRequest;
import com.haiph.userservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<ResponseBody> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            authService.login(request)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseBody> register(@RequestBody UserRequest request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            authService.register(request)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @PostMapping("/token/{username}")
    public ResponseEntity<ResponseBody> genarateToken(@PathVariable String username) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            authService.generateToken(username)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }
}
