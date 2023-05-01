package com.haiph.common.enums.status.menuService.payment;

public enum PaymenStatus {
    QR("Thanh Toán Bằng QR"),
    CASH("Thanh Toán Tiền Mặt"),
    BANK("Thanh Toán Chuyển Khoản"),
    ;
    private String description;

    PaymenStatus(String description) {
        this.description = description;
    }
}
