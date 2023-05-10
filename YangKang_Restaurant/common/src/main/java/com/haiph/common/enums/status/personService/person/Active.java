package com.haiph.common.enums.status.personService.person;

public enum Active {
    NOT_ACTIVE("Chưa kích hoạt"),
    ACTIVE("Đã kích hoạt");
    private String description;

    Active(String description) {
        this.description = description;
    }
}
