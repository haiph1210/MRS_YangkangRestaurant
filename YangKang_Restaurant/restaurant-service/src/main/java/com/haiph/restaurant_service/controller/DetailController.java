package com.haiph.restaurant_service.controller;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.restaurant_service.dto.request.detail.DetailCreateRequest;
import com.haiph.restaurant_service.dto.request.detail.DetailUpdateRequest;
import com.haiph.restaurant_service.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/restaurant/detail")
public class DetailController {

    @Autowired
    private DetailService detailService;

    @GetMapping("/findAll")
    public ResponseEntity<ResponseBody> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            detailService.findAllPage(pageable)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/findId/{id}")
    public ResponseEntity<ResponseBody> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            detailService.findById(id)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/findName/{name}")
    public ResponseEntity<ResponseBody> findByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            detailService.findByName(name)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> create(@RequestBody DetailCreateRequest request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            detailService.create(request)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBody> update(@RequestBody DetailUpdateRequest request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            detailService.update(request)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseBody> delete(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            detailService.deleteById(id)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

}
