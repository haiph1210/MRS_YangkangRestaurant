//package com.haiph.userservice.entity.bank;
//
//import com.haiph.common.enums.status.personService.BankName;
//import com.haiph.userservice.entity.person.Person;
//import jakarta.persistence.*;
//import jakarta.ws.rs.DefaultValue;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table
//@AllArgsConstructor(staticName = "build")
//@NoArgsConstructor
//@Getter
//@Setter
//public class BankUser {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    @Column(unique = true)
//    private String personCode;
//    private String bankUserNumber; // số tài khoản
//    private String userOwenrName; // tên chủ tài khoản
//    @Enumerated(EnumType.STRING)
//    private BankName bankName; // tên ngân hàng
//    @DefaultValue("0")
//    private Double countPrice;
//
//
//    public BankUser(String personCode, String bankUserNumber, String userOwenrName, BankName bankName, Double countPrice) {
//        this.personCode = personCode;
//        this.bankUserNumber = bankUserNumber;
//        this.userOwenrName = userOwenrName;
//        this.bankName = bankName;
//        this.countPrice = countPrice;
//    }
//}
