package com.haiph.employeeservice.controller;


import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.employeeservice.dto.request.PositionRequest;
import com.haiph.employeeservice.dto.request.TimeSheetingDayRequest;
import com.haiph.employeeservice.entity.EmployeeId;
import com.haiph.employeeservice.service.TimeSheetingDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/timeSheetingDay")
public class TimeSheetingDayController {
    @Autowired
    private TimeSheetingDayService timeSheetingDayService;

    @GetMapping("/findAll")
    public ResponseEntity<ResponseBody> findAll() {
        try {
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS.getResponseCode(), Response.SUCCESS.getResponseMessage(), timeSheetingDayService.findAll()));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/findPage")
    public ResponseEntity<ResponseBody> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS.getResponseCode(), Response.SUCCESS.getResponseMessage(), timeSheetingDayService.findAll(pageable)));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/findId/{id}")
    public ResponseEntity<ResponseBody> findById(@RequestBody EmployeeId id) {
        try {
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS.getResponseCode(), Response.SUCCESS.getResponseMessage(), timeSheetingDayService.findById(id)));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> create(@RequestBody TimeSheetingDayRequest request) {
        try {
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS.getResponseCode(), Response.SUCCESS.getResponseMessage(), timeSheetingDayService.create(request)));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBody> updatde(@RequestBody TimeSheetingDayRequest request) {
        try {
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS.getResponseCode(),
                    Response.SUCCESS.getResponseMessage(), timeSheetingDayService.update( request)));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<ResponseBody> delete(@RequestBody EmployeeId id) {
        try {
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS.getResponseCode(), Response.SUCCESS.getResponseMessage(), timeSheetingDayService.delete(id)));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }
}
