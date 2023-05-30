package com.haiph.userservice.controller;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.userservice.dto.request.UserChangePassword;
import com.haiph.userservice.dto.request.UserRequest;
import com.haiph.userservice.dto.request.sercurity.LoginRequest;
import com.haiph.userservice.service.AuthService;
import com.haiph.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

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
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody("9999", e.getMessage()));
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
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseBody> changePassword(@RequestBody UserChangePassword changePassword) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            userService.changePassword(changePassword)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
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
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<ResponseBody> validateToken(@PathVariable String token) {
        authService.validateToken(token);
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage()
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/active")
    public ResponseEntity<ResponseBody> activeUser(@RequestParam String userCode) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.activeUserByUserCode(userCode)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

}
