package com.haiph.restaurant_service.dto.response.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterialDTO {
    private String name;
    private Integer quantity;
    private String detailName;
}
