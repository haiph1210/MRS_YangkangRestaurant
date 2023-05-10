package com.haiph.menuservice.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class SearchFormOrder {
    private String search;
    private Double minTotalPrice;
    private Double maxTotalPrice;
    private LocalDateTime hour;
    private Integer type;
    private Integer status;
}
