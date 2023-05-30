package com.haiph.authservice.controller;

import com.haiph.authservice.request.AuthRequest;
import com.haiph.authservice.request.UserChangePassword;
import com.haiph.authservice.request.UserRequest;
import com.haiph.authservice.service.AuthService;
import com.haiph.authservice.service.impl.AuthServiceImpl;
import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.common.sercurity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/authenticate")
public class AuthController {
  @Autowired
  private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<ResponseBody> login(@RequestBody AuthRequest request) {
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

    @PutMapping("/change-password")
    public ResponseEntity<ResponseBody> changePassword(@RequestBody UserChangePassword request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            authService.changePassword(request)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

}
