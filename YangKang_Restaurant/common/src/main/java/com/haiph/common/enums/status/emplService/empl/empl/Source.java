package com.haiph.common.enums.status.emplService.empl.empl;

import lombok.Getter;

@Getter
public enum Source {
    MAY_CHAM_CONG("Máy Chấm Công"),
    CHAM_CONG_BU("Chấm Công Bù"),
    QUAN_LI_CHAM_CONG("Quản Lí Chấm Công");
    private String description;

    Source(String description) {
        this.description = description;
    }
}
