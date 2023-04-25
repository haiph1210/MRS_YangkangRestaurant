package com.haiph.menuservice.dto.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchFormMenu {
    private String search;
    private Double minPrice;
    private Double maxPrice;
    private String imgUrl;
    private String description;
}
