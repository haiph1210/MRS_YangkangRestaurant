package com.haiph.menuservice.entity;

import com.haiph.common.enums.status.menuService.payment.PaymenStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String paymentCode;
    private Integer orderId;
    private Integer discountId;
    private Double totalPrice;
    private Double customerPay;
    private Double remain;
    @Enumerated(EnumType.ORDINAL)
    private PaymenStatus status;
    private Double score;
    private LocalDateTime createDate;

    @PrePersist
    public void prePersit() {
        if (this.remain == null){
            this.remain = this.customerPay - this.totalPrice;
        }if (this.createDate== null) {
            this.createDate = LocalDateTime.now();
        }
    }

    public Payment(String paymentCode, Integer orderId, Integer discountId, Double totalPrice, Double customerPay, Double remain, PaymenStatus status, Double score, LocalDateTime createDate) {
        this.paymentCode = paymentCode;
        this.orderId = orderId;
        this.discountId = discountId;
        this.totalPrice = totalPrice;
        this.customerPay = customerPay;
        this.remain = remain;
        this.status = status;
        this.score = score;
        this.createDate = createDate;
    }
}
