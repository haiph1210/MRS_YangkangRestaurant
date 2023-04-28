package com.haiph.restaurant_service.dto.request.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RestaurantFormCreate {
    private String masterialName;
}
