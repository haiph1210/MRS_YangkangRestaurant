package com.haiph.userservice.controller;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import com.haiph.common.exception.CommonException;
import com.haiph.userservice.model.request.employee.EmployeeRequest;
import com.haiph.userservice.model.request.user.UserRequest;

import com.haiph.userservice.service.EmployeeService;
import com.haiph.userservice.service.PersonService;
import com.haiph.userservice.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/person")
@Validated
public class PersonController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    @Qualifier("personServiceImpl")
    private PersonService personService;


    @GetMapping("/findPer")
    public ResponseEntity<ResponseBody> findAllPerson(Pageable pageable) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            personService.findAllPage(pageable)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/findPerId/{id}")
    public ResponseEntity<ResponseBody> findById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            personService.findById(id)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/findPerCode/{personCode}")
    public ResponseEntity<ResponseBody> findByPersonCode(@PathVariable String personCode) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            personService.findByPersonCode(personCode)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }
    /*
     * User
     * */


    @GetMapping("/findPageUser")
    public ResponseEntity<ResponseBody> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS.getResponseCode(),
                            Response.SUCCESS.getResponseMessage(),
                            userService.findAllPage(pageable)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @GetMapping("/findUserId/{id}")
    public ResponseEntity<ResponseBody> findUserById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(Response.SUCCESS.getResponseCode()
                            , Response.SUCCESS.getResponseMessage(),
                            userService.findById(id)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<ResponseBody> createUser(@RequestBody @Valid UserRequest userCreate) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(Response.SUCCESS.getResponseCode()
                            , Response.SUCCESS.getResponseMessage(),
                            userService.create(userCreate)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }


    @PutMapping("/update-user")
    public ResponseEntity<ResponseBody> updateUser(@PathVariable UUID id, @RequestBody @Valid UserRequest userUpdate) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(Response.SUCCESS.getResponseCode()
                            , Response.SUCCESS.getResponseMessage(),
                            userService.update(id, userUpdate)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }


    @DeleteMapping("delete-user")
    public ResponseEntity<ResponseBody> deleteUserById(@PathVariable UUID id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok(
                    new ResponseBody(Response.SUCCESS.getResponseCode()
                            , Response.SUCCESS.getResponseMessage()
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }


    /*
    Employee
     */

    @GetMapping("/findPageEmpl")
    public ResponseEntity<ResponseBody> findAllDTO(Pageable pageable) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(Response.SUCCESS.getResponseCode()
                            , Response.SUCCESS.getResponseMessage(),
                            employeeService.findAllPage(pageable)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }

    }

    @GetMapping("/findEmplId/{id}")
    public ResponseEntity<ResponseBody> findEmployeeById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(Response.SUCCESS.getResponseCode()
                            , Response.SUCCESS.getResponseMessage(),
                            employeeService.findById(id)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }

    }

    @PostMapping("/create-empl")
    public ResponseEntity<ResponseBody> createEmployee(@RequestBody @Valid EmployeeRequest employeeCreate) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(Response.SUCCESS.getResponseCode()
                            , Response.SUCCESS.getResponseMessage(),
                            employeeService.create(employeeCreate)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }

    }

    @PutMapping("/update-empl/{id}")
    public ResponseEntity<ResponseBody> updateEmployee(@PathVariable UUID id, @RequestBody @Valid EmployeeRequest employeeUpdate) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(Response.SUCCESS,
                            employeeService.update(id, employeeUpdate)
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }

    }

    @DeleteMapping("delete-empl/{id}")
    public ResponseEntity<ResponseBody> deleteEmployeeById(@PathVariable UUID id) {
        try {
            employeeService.deleteById(id);
            return ResponseEntity.ok(
                    new ResponseBody(Response.SUCCESS.getResponseCode()
                            , Response.SUCCESS.getResponseMessage()
                    ));
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }

    }

    @GetMapping("/active")
    public ResponseEntity<ResponseBody> activeAccount(@RequestParam("personCode") String personCode) {
        try {
            return ResponseEntity.ok(
                    new ResponseBody(
                            Response.SUCCESS,
                            personService.activePerson(personCode)
                    )
            );
        } catch (CommonException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(), exception.getMessage()));
        }
    }
}