package com.haiph.common.enums.status.menuService.order;

import lombok.Getter;

@Getter
public enum OrderType {
    TAKE_HOME("MANG VỀ NHÀ"),
    RESTAURANT("TẠI NHÀ HÀNG")
    ;
    private String description;

    OrderType(String description) {
        this.description = description;
    }
}
