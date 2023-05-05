package com.haiph.menuservice.dto.response;

import com.haiph.common.enums.status.menuService.payment.PaymenStatus;
import com.haiph.menuservice.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class PaymentResponse {
    private Integer id;
    private String paymentCode;
    private OrderResponse orderResponse;
    private DiscountResponse discountResponse;
    private Double totalPrice;
    private Double customerPay;
    private Double remain;
    private PaymenStatus status;
    private Double score;
    private LocalDateTime createDate;
}
