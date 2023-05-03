package com.haiph.common.enums.status.personService.person;

public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ");
    private String description;

    Gender(String description) {
        this.description = description;
    }
}
