package com.haiph.common.enums.status.menuService.discount;

import lombok.Getter;

@Getter
public enum PercentDiscount {
    ONE(1, "1%"),
    TWO(2, "2%"),
    FIVE(5, "5%"),
    TEN(10, "10%");
    private Integer percent;
    private String description;

    PercentDiscount(Integer percent, String description) {
        this.percent = percent;
        this.description = description;
    }
}
