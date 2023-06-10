package com.haiph.menuservice.controller;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.menuservice.dto.request.CartRequest;
import com.haiph.menuservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
public class CartController {

        @Autowired
        private CartService cartService;

        @GetMapping("/findPage")
        public ResponseEntity<ResponseBody> findAll(Pageable pageable) {
            try {
                return ResponseEntity.ok(
                        new ResponseBody(
                                Response.SUCCESS.getResponseCode(),
                                Response.SUCCESS.getResponseMessage(),
                                cartService.findAll(pageable)
                        )
                );
            } catch (CommonException exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
            }
        }


        @GetMapping("/id/{id}")
        public ResponseEntity<ResponseBody> findById(@PathVariable Integer id) {
            try {
                return ResponseEntity.ok(
                        new ResponseBody(
                                Response.SUCCESS.getResponseCode(),
                                Response.SUCCESS.getResponseMessage(),
                                cartService.findById(id)
                        )
                );
            } catch (CommonException exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
            }
        }

    @GetMapping("/userCode/{userCode}")
    public ResponseEntity<ResponseBody> mapToUserCodeAndCartResponse(@PathVariable String userCode) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            cartService.findUserCodeToListCart(userCode)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/userCodeV2/{userCode}")
    public ResponseEntity<ResponseBody> mapToUserCodeAndCartResponseV2(@PathVariable String userCode) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            cartService.findByUserCodeV2(userCode)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/findUserCode/{userCode}")
    public ResponseEntity<ResponseBody> findByUserCode(@PathVariable String userCode) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            cartService.findByUserCode(userCode)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }
    @GetMapping("/total/{userCode}")
    public ResponseEntity<ResponseBody> total(@PathVariable String userCode) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            cartService.totalCart(userCode)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

        @PostMapping("/create")
        public ResponseEntity<ResponseBody> create(@RequestBody CartRequest request) {
            try {
                return ResponseEntity.ok(
                        new ResponseBody(
                                Response.SUCCESS.getResponseCode(),
                                Response.SUCCESS.getResponseMessage(),
                                cartService.create(request)
                        )
                );
            } catch (CommonException exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
            }
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<ResponseBody> update(@PathVariable Integer id, @RequestBody CartRequest request) {
            try {
                return ResponseEntity.ok(
                        new ResponseBody(
                                Response.SUCCESS.getResponseCode(),
                                Response.SUCCESS.getResponseMessage(),
                                cartService.update(id, request)
                        )
                );
            } catch (CommonException exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
            }
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<ResponseBody> delete(@PathVariable Integer id) {
            try {
                return ResponseEntity.ok(
                        new ResponseBody(
                                Response.SUCCESS.getResponseCode(),
                                Response.SUCCESS.getResponseMessage(),
                                cartService.delete(id)
                        )
                );
            } catch (CommonException exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
            }
        }


}

