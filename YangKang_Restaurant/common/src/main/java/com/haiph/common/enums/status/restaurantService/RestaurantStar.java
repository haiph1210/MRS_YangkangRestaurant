package com.haiph.common.enums.status.restaurantService;

import lombok.Getter;

@Getter
public enum RestaurantStar{
    ONE("MỘT SAO",1),
    TWO("HAI SAO",2),
    THREE("BA SAO",3),
    FOUR("BỐN SAO",4),
    FIVE("NĂM SAO",5)

    ;
    private String description;
    private Integer star;

    RestaurantStar(String description, Integer star) {
        this.description = description;
        this.star = star;
    }
}
