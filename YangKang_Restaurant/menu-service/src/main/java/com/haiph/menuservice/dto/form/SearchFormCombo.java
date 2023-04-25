package com.haiph.menuservice.dto.form;

import com.haiph.common.status.combo.NumberOfPeople;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchFormCombo {
    private String search;
    private Double minPrice;
    private Double maxPrice;
    private String numberOfPeople;
    private String description;
}
