package com.haiph.authservice.controller;

import com.haiph.authservice.request.AuthRequest;
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
  private AuthServiceImpl authService;
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

    @GetMapping("/test")
    public UserInfo getMessage() {
        return authService.getUserInfo();
    }
}
