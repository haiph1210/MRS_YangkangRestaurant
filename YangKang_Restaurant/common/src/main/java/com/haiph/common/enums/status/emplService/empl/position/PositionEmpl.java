package com.haiph.common.enums.status.emplService.empl.position;

import lombok.Getter;

@Getter
public enum PositionEmpl {

    SERVE("Phục Vụ"),
    CHEF("Đầu Bếp"),
    MANAGER("Quản Lí"),
    SERVICE("Tạp vụ");
    private String description;

    PositionEmpl(String description) {
        this.description = description;
    }
}
