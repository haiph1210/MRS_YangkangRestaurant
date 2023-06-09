package com.haiph.restaurant_service.controller;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;

import com.haiph.restaurant_service.dto.request.form.RestaurantFormCreate;
import com.haiph.restaurant_service.dto.request.form.RestaurantFormUpdate;
import com.haiph.restaurant_service.service.RestaurantFormService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/restaurant/form")
public class RestaurantFormController {
    @Autowired
    private RestaurantFormService formService;
    @GetMapping("/findAll")
    public ResponseEntity<ResponseBody> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            formService.findAllPage(pageable)
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
                            formService.findById(id)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/formCode")
    public ResponseEntity<ResponseBody> findByName(@PathParam("formCode") String formCode) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            formService.findByFormCode(formCode)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> create(@RequestBody RestaurantFormCreate request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            formService.create(request)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBody> update(@RequestBody RestaurantFormUpdate request) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            formService.update(request)
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
                            formService.deleteById(id)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @PutMapping("/updateReady/{id}")
    public ResponseEntity<ResponseBody> updateReady(@PathVariable List<Integer> id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            formService.updateReady(id)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }
    @PutMapping("/updatePending/{id}")
    public ResponseEntity<ResponseBody> updatePending(@PathVariable List<Integer> id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            formService.updatePending(id)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }
    @PutMapping("/updateRefuse/{id}")
    public ResponseEntity<ResponseBody> updateRefuse(@PathVariable List<Integer> id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            formService.updateRefuse(id)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }
    @PutMapping("/updateBooked/{id}")
    public ResponseEntity<ResponseBody> updateBooked(@PathVariable List<Integer> id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            formService.updateBooked(id)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }


    @GetMapping("findList/{ids}")
    public ResponseEntity<ResponseBody> findByListId(@PathVariable List<Integer> ids) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            formService.findByListId(ids)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

}
