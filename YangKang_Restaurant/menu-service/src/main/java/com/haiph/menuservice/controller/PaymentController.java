package com.haiph.menuservice.controller;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.menuservice.dto.form.SearchFormOrder;
import com.haiph.menuservice.dto.request.OrderRequest;
import com.haiph.menuservice.dto.request.PaymentRequest;
import com.haiph.menuservice.service.OrderService;
import com.haiph.menuservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService service;

    @GetMapping("/findAll")
    public ResponseEntity<ResponseBody> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            service.findAllPage(pageable)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/findId/{id}")
    public ResponseEntity<ResponseBody> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            service.findById(id)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/findListId/{ids}")
    public ResponseEntity<ResponseBody> findByListId(@PathVariable List<Integer> ids) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            service.findListId(ids)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }


//    @GetMapping("/findForm")
//    public ResponseEntity<ResponseBody> findForm(@RequestBody SearchFormOrder formOrder) {
//        try {
//            return ResponseEntity.ok(
//                    new ResponseBody(
//                            Response.SUCCESS.getResponseCode(),
//                            Response.SUCCESS.getResponseMessage(),
//                            service.findFormOrder(formOrder)
//                    )
//            );
//        } catch (CommonException exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
//        }
//    }





    @PostMapping("/create")
    public ResponseEntity<ResponseBody> create(@RequestBody PaymentRequest request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            service.create(request)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseBody> update(@PathVariable Integer id, @RequestBody PaymentRequest request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            service.update(id, request)
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
                            service.delete(id)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }
}
