package com.haiph.restaurant_service.dto.response;

import com.haiph.restaurant_service.entity.Detail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class MasterialResponse {
    private Integer id;
    private String name;
    private Integer quantity;
    private Double price;
    private Double initPrice;
    private String detailName;
}
