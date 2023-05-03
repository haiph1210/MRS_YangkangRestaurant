//package com.haiph.userservice.controller;
//
//import com.haiph.common.dto.response.Response;
//import com.haiph.common.dto.response.ResponseBody;
//import com.haiph.common.exception.CommonException;
//
//import com.haiph.userservice.entity.bank.BankUser;
//import com.haiph.userservice.model.request.banks.BankUserCreate;
//import com.haiph.userservice.model.request.banks.BankUserUpdate;
//import com.haiph.userservice.repository.person.PersonRepository;
//import com.haiph.userservice.service.BankUserService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/user")
//public class BankUserController {
//    @Autowired
//    private BankUserService bankUserService;
//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private ModelMapper mapper;
//
//
//
//    @GetMapping("/findBank")
//    public ResponseEntity<ResponseBody> findAll() {
//        try {
//            return ResponseEntity.ok(
//                    new com.haiph.common.dto.response.ResponseBody(
//                            Response.SUCCESS.getResponseCode(),
//                            Response.SUCCESS.getResponseMessage(),
//                            bankUserService.findAll()
//                    )
//            );
//        }catch (CommonException exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
//        }
//    }
//
//    @GetMapping("/find-DTO-bank")
//    public ResponseEntity<ResponseBody>  findAllDTO(Pageable pageable) {
//        try {
//            return ResponseEntity.ok(
//                    new ResponseBody(
//                            Response.SUCCESS.getResponseCode(),
//                            Response.SUCCESS.getResponseMessage(),
//                            bankUserService.findAllPage(pageable)
//                    )
//            );
//        }catch (CommonException exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
//        }
//    }
//
//    @GetMapping("/findBankId/{id}")
//    public ResponseEntity<ResponseBody> findBankById(@PathVariable Integer id) {
//        try {
//            return ResponseEntity.ok(
//                    new ResponseBody(
//                            Response.SUCCESS.getResponseCode(),
//                            Response.SUCCESS.getResponseMessage(),
//                            bankUserService.findById(id)
//                    )
//            );
//        }catch (CommonException exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
//        }
//    }
//    @PostMapping("/createBank")
//    public ResponseEntity<ResponseBody> createBank(@RequestBody BankUserCreate bankUserCreate) {
//        try {
//            return ResponseEntity.ok(
//                    new ResponseBody(
//                            Response.SUCCESS.getResponseCode(),
//                            Response.SUCCESS.getResponseMessage(),
//                            bankUserService.create(bankUserCreate)
//                    )
//            );
//        }catch (CommonException exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(exception.getResponse(),exception.getMessage()));
//        }
//    }
//    @PutMapping("/update-bank")
//    public ResponseEntity<ResponseBody> updateBank(@RequestBody BankUserUpdate bankUserUpdate) {
//        BankUser update = bankUserService.update(bankUserUpdate);
//        return ResponseEntity.ok(
//                new ResponseBody(
//                        Response.SUCCESS.getResponseCode(),
//                        Response.SUCCESS.getResponseMessage(),
//                        update
//                ));
//    }
//    @DeleteMapping("delete-bank")
//    public ResponseEntity<ResponseBody> deleteBankById(@PathVariable Integer id) {
//        bankUserService.deleteById(id);
//        return ResponseEntity.ok(
//                new ResponseBody(
//                        Response.SUCCESS.getResponseCode(),
//                        Response.SUCCESS.getResponseMessage()
//                ));
//    }
//
//    @PostMapping("/sendMail")
//    public ResponseEntity<ResponseBody> sendMail(@RequestBody SendMail sendMail) {
//
//        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS,emailService.sendMail2(sendMail.getEmailRevice(),sendMail.getSubject(),sendMail.getMessage())));
//    }
//}
