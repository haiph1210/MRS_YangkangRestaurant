package com.haiph.restaurant_service.dto.request.masterial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class MasterialCreateRequest {
    private String name;
    private Integer quantity;
    private Double price;
    private Integer detailId;
}
