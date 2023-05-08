package com.haiph.common.enums.status.personService.person;

public enum Role {
    USER("Quyền Người Dùng"),
    ADMIN("Quyền Quản Trị Viên");
    private String description;

    Role(String description) {
        this.description = description;
    }
}
