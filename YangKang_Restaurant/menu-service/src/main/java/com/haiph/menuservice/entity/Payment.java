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
    private String personCode;
    private String paymentCode;
    @ElementCollection
    @CollectionTable(name = "payment_order_ids", joinColumns = @JoinColumn(name = "payment_id"))
    @JoinColumn(name = "`payment_id`", referencedColumnName = "`id`")
    private List<Integer> orderIds;
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

    public Payment(String personCode, String paymentCode, List<Integer> orderId, Integer discountId, Double totalPrice, Double customerPay, Double remain, PaymenStatus status, Double score, LocalDateTime createDate) {
        this.personCode = personCode;
        this.paymentCode = paymentCode;
        this.orderIds = orderId;
        this.discountId = discountId;
        this.totalPrice = totalPrice;
        this.customerPay = customerPay;
        this.remain = remain;
        this.status = status;
        this.score = score;
        this.createDate = createDate;
    }
}
