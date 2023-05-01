package com.haiph.menuservice.dto.request;

import com.haiph.common.enums.status.menuService.payment.PaymenStatus;
import com.haiph.menuservice.dto.response.DiscountResponse;
import com.haiph.menuservice.dto.response.OrderResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class PaymentRequest {
    private String personCode;
    private List<Integer> orderIds;
    private Integer discountId;
    private Double customerPay;
    private PaymenStatus status;
}
