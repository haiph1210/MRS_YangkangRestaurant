package com.haiph.userservice.controller;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.userservice.dto.request.UserRequest;
import com.haiph.userservice.dto.request.UserUpdateAvartar;
import com.haiph.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/findAll")
    public ResponseEntity<ResponseBody> findAll() {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.findAll()
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @GetMapping("/findPage")
    public ResponseEntity<ResponseBody> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.findAllPage(pageable)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

//    @PostMapping("/search-form")
//    public ResponseEntity<ResponseBody> searchForm(@RequestBody SearchFormMenu request) {
//        try {
//            return ResponseEntity.ok(
//                    new ResponseBody(
//                            Response.SUCCESS.getResponseCode(),
//                            Response.SUCCESS.getResponseMessage(),
//                            userService.findForm(request)
//                    )
//            );
//        }catch (CommonException exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
//        }
//    }
    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseBody> findById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.findById(id)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<ResponseBody> findByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.findByUsername(username)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseBody> findByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.findByEmail(email)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @GetMapping("/userCode/{userCode}")
    public ResponseEntity<ResponseBody> findByuserCode(@PathVariable String userCode) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.findByUserCode(userCode)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }


//    @GetMapping("/findList/{ids}")
//    public ResponseEntity<ResponseBody> findListId(@PathVariable List<Integer> ids) {
//        try {
//            return ResponseEntity.ok(
//                    new ResponseBody(
//                            Response.SUCCESS.getResponseCode(),
//                            Response.SUCCESS.getResponseMessage(),
//                            userService.findByListId(ids)
//                    )
//            );
//        }catch (CommonException exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
//        }
//    }

    @PostMapping("/createImg")
    public ResponseEntity<ResponseBody> createImage(@ModelAttribute UserRequest request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.create(request)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @PostMapping("/updateAvartar")
    public ResponseEntity<ResponseBody> updateAvarta(@ModelAttribute UserUpdateAvartar request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.updateAvatar(request)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> create(@RequestBody UserRequest request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.createToLogin(request)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<ResponseBody> update(@PathVariable UUID id,@ModelAttribute UserRequest request) {
//        try {
//            return ResponseEntity.ok(
//                    new ResponseBody(
//                            Response.SUCCESS.getResponseCode(),
//                            Response.SUCCESS.getResponseMessage(),
//                            userService.Update(id,request)
//                    )
//            );
//        }catch (CommonException exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
//        }
//    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseBody> delete(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.delete(id)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> readFileName(@RequestParam String fileName) {
        byte[] bytes = userService.readFileImg(fileName);
        try {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }catch (CommonException exception) {
            return ResponseEntity.noContent().build();
        }
    }


}
