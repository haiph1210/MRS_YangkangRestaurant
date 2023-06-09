package com.haiph.menuservice.controller;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.menuservice.dto.form.SearchFormCombo;
import com.haiph.menuservice.dto.request.ComboRequest;
import com.haiph.menuservice.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combo")
public class ComboController {
    @Autowired
    private ComboService comboService;

    @GetMapping("/findAll")
    public ResponseEntity<ResponseBody> findAll() {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            comboService.findAll()
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @GetMapping("/findList/{ids}")
    public ResponseEntity<ResponseBody> findAll(@PathVariable List<Integer> ids) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            comboService.findByListId(ids)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> create(@RequestBody ComboRequest request) {
        return ResponseEntity.ok(
                new ResponseBody(
                        Response.SUCCESS,
                        comboService.create(request)
                )
        );
    }

    @GetMapping("/findPage")
    public ResponseEntity<ResponseBody> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            comboService.findAll(pageable)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @PostMapping("/search-form")
    public ResponseEntity<ResponseBody> searchForm(@RequestBody SearchFormCombo request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            comboService.findForm(request)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseBody> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            comboService.findById(id)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseBody> findByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            comboService.findByName(name)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<ResponseBody> findByPrice(@PathVariable Double price) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            comboService.findByPrice(price)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseBody> update(@PathVariable Integer id, @RequestBody ComboRequest request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            comboService.update(id,request)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseBody> delete(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            comboService.deleteById(id)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseBody> deleteAllById(@RequestBody List<Integer> ids) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            comboService.deleteListById(ids)
                    )
            );
        }catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
        }
    }
}
