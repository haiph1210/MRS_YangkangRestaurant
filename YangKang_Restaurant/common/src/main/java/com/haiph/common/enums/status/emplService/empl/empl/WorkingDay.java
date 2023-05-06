package com.haiph.common.enums.status.emplService.empl.empl;

import lombok.Getter;

@Getter
public enum WorkingDay {
    FULL("8h"),
    HALF_DAY("4h");
    private String description;

    WorkingDay(String description) {
        this.description = description;
    }
}
