package com.haiph.menuservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Table
@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userCode;
    private String code;
    private Double price;
    private Integer amount;
    private Double initPrice;
    @CreationTimestamp
    private LocalDate createDate;
    @PrePersist
    public void prePersist() {
        if (this.amount == null) {
            this.amount = 1;
        }
        if (this.initPrice == null) {
            this.initPrice = this.amount * this.price;
        }
    }
//create


    public Cart(String userCode, String code, Double price, Integer amount) {
        this.userCode = userCode;
        this.code = code;
        this.price = price;
        this.amount = amount;
    }
}
