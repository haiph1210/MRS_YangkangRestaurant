package com.haiph.restaurant_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Masterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer quantity;
    private Double price;
    private Double initPrice;
    @ManyToOne()
    @JoinColumn(name = "detail_id",referencedColumnName = "id")
    private Detail detail;

    @PrePersist
    public void prePersist() {
        if (this.initPrice == null) {
            this.initPrice = this.price * this.quantity;
        }
    }

    public void setInitPrice(Double initPrice) {
        this.initPrice = this.price * this.quantity;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }
}
