package com.haiph.common.enums.status.restaurantService;

import lombok.Getter;

@Getter
public enum RestaurantStar{
    ONE("MỘT SAO"),
    TWO("HAI SAO"),
    THREE("BA SAO"),
    FOUR("BỐN SAO"),
    FIVE("NĂM SAO")

    ;
    private String description;

    RestaurantStar(String description) {
        this.description = description;
    }
}
