package com.haiph.menuservice.dto.response;

import com.haiph.common.enums.status.menuService.menu.NumberOfPeople;
import com.haiph.common.enums.status.menuService.order.OrderStatus;
import com.haiph.common.enums.status.menuService.order.OrderType;
import com.haiph.menuservice.dto.response.restApi.PersonResponse;
import com.haiph.menuservice.dto.response.restApi.RestaurantFormResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class OrderResponse {
    private Integer id;
    private String orderCode;
    private PersonResponse personResponses;
    private List<MenuResponse> menus;
    private List<ComboResponse> combos;
    private List<CartResponse> carts;
    private List<RestaurantFormResponse> forms;
    private NumberOfPeople people;
    private Integer totalAmount;
    private Double totalPrice;
    private LocalDate createDate;
    private LocalDateTime hour;
    private String description;
    private OrderType type;
    private OrderStatus status;
}
