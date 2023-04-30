package com.haiph.common.enums.status.menuService.discount;

public enum DiscountStatus {
    CREATE("ĐƯỢC TẠO"),
    EXPIRED("HẾT HẠN"),

    ;
    private String description;

    DiscountStatus(String description) {
        this.description = description;
    }
}
