package com.haiph.common.enums.status.menuService.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("ĐANG CHỜ"),
    APPROVED("ĐÃ DUYỆT"),
    REFUSE("ĐÃ HỦY")
    ;
    private String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
