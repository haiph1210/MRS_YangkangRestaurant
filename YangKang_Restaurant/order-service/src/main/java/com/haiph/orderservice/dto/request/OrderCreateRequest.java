package com.haiph.orderservice.dto.request;

import com.haiph.common.enums.status.order.OrderStatus;
import com.haiph.common.enums.status.order.OrderType;
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
@NoArgsConstructor()
public class OrderCreateRequest {
    private List<Integer> menuIds;
    private List<Integer> comboIds;
    private Integer totalAmount;
    private Double totalPrice;
    private OrderStatus status;
    private OrderType type;
    private Integer restaurantDetailId; // form RestaurantDetail
    private LocalDate createDate;
    private LocalDateTime hour;
    private String description;
}
