package com.haiph.menuservice.entity;

import com.haiph.common.enums.status.menu.NumberOfPeople;
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

@Entity
@Table
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(unique = true)
    private String orderCode;
    @ElementCollection
    @CollectionTable(name = "order_menu_ids", joinColumns = @JoinColumn(name = "order_id"))
    @JoinColumn(name = "`order_id`", referencedColumnName = "`id`")
    private List<Integer> idMenus;
    @ElementCollection
    @CollectionTable(name = "order_combo_ids", joinColumns = @JoinColumn(name = "order_id"))
    @JoinColumn(name = "`order_id`", referencedColumnName = "`id`")
    private List<Integer> idCombos;
    @ElementCollection
    @CollectionTable(name = "order_form_ids", joinColumns = @JoinColumn(name = "order_id"))
    @JoinColumn(name = "`order_id`", referencedColumnName = "`id`")
    private List<Integer> idForms;
    private Integer totalAmount;
    private Double totalPrice;
    @CreationTimestamp
    private LocalDate createDate;
    private LocalDateTime hour;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private OrderType type;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;
    @Enumerated(EnumType.STRING)
    private NumberOfPeople peoples;

    public Order(String orderCode, List<Integer> idMenus, List<Integer> idCombos, List<Integer> idForm, Integer totalAmount, Double totalPrice, LocalDate createDate, LocalDateTime hour, String description, OrderType type, OrderStatus status, NumberOfPeople peoples) {
        this.orderCode = orderCode;
        this.idMenus = idMenus;
        this.idCombos = idCombos;
        this.idForms = idForm;
        this.totalAmount = totalAmount;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.hour = hour;
        this.description = description;
        this.type = type;
        this.status = status;
        this.peoples = peoples;
    }
}
