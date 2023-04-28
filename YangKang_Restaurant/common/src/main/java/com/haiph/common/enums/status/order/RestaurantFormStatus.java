package com.haiph.common.enums.status.order;

import lombok.Getter;

@Getter
public enum RestaurantFormStatus {
    READY("Sẵn sàng"),
    PENDING("Chờ"),
    MAINTENANCE("Bảo trì"),
    BOOKED("Được Đặt")
   ;
    private String description;

    RestaurantFormStatus(String description) {
        this.description = description;
    }
}
