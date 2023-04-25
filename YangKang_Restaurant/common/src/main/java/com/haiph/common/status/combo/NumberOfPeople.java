package com.haiph.common.status.combo;

public enum NumberOfPeople {
    FOUR(4,"COMBO 4 NGƯỜI"),
    EIGHT(8,"COMBO 8 NGƯỜI"),
    TEN(10,"COMBO 10 NGƯỜI"),
    TWIFTEEN(12,"COMBO 12 NGƯỜI"),
    TWIFTY(12,"COMBO 12 NGƯỜI"),
    FULL(50,"ĐẶT TIỆC");

    NumberOfPeople(Integer totalPeople,String description) {
        this.totalPeople = totalPeople;
        this.description = description;
    }

    private final Integer totalPeople;
    private final String description;

}
