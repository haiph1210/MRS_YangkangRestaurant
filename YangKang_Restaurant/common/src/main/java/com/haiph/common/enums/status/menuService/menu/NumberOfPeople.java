package com.haiph.common.enums.status.menuService.menu;

public enum NumberOfPeople {
    TEN(10,"COMBO 10 NGƯỜI"),
    FIVETEEN(15,"COMBO 15 NGƯỜI"),
    TWENTY(20,"COMBO 20 NGƯỜI"),
    THIRTY(30,"COMBO 30 NGƯỜI"),
    FIVETY(50,"COMBO 50 NGƯỜI"),
    ONE(1,"ĐẶT TIỆC - 1 Tầng ");

    NumberOfPeople(Integer totalPeople,String description) {
        this.totalPeople = totalPeople;
        this.description = description;
    }

    private final Integer totalPeople;
    private final String description;

}
