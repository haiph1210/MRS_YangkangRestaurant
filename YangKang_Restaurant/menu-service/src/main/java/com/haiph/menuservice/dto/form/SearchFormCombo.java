package com.haiph.menuservice.dto.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchFormCombo {
    private String search;
    private Double minPrice;
    private Double maxPrice;
}
