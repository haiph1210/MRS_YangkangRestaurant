package com.haiph.common.enums.status.personService.empl;

public enum PositionEmpl {

    SERVE("phục vụ"),
    CHEF("Đầu Bếp"),
    MANAGER("Quản Lí"),
    SERVICE("Tạp vụ");
    private String description;

    PositionEmpl(String description) {
        this.description = description;
    }
}
