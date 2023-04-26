package com.haiph.orderservice.entity;

import com.haiph.common.enums.status.order.OrderStatus;
import com.haiph.common.enums.status.order.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ElementCollection
    @CollectionTable(name = "order_menu_ids", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "menu_id")
    private List<Integer> menuIds;
    @ElementCollection
    @CollectionTable(name = "order_combo_ids", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "combo_id")
    private List<Integer> comboIds;
    private Integer totalAmount;
    private Double totalPrice;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;
    @Enumerated(EnumType.ORDINAL)
    private OrderType type;
    private Integer restaurantDetailId; // form RestaurantDetail
    @CreationTimestamp
    private LocalDate createDate;
    private LocalDateTime hour;
    private String description;

    @PrePersist
    public void prePersist() {
        if (totalAmount == null) {
            this.totalAmount = 0;
        }
        if (this.totalPrice == null) {
            this.totalPrice = 0d;
        }
        if (this.status == null) {
            this.status = OrderStatus.PENDING;
        }
        if (this.type == OrderType.TAKE_HOME) {
            this.restaurantDetailId = 1;
            this.hour = LocalDateTime.now();
        }if(this.hour == null) {
            this.hour = LocalDateTime.now();
        }
    }
}
