package com.haiph.common.enums.status.personService.user;

public enum UserType {
    VIP("Khách VIP"), POTENTIAL("Khách Hàng Tiềm Năng"), NORMAL("Khách Hàng Thường");

    private String description;

    UserType(String description) {
        this.description = description;
    }
}