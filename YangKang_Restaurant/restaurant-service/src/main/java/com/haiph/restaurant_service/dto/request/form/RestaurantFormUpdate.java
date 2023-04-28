package com.haiph.restaurant_service.dto.request.form;

import com.haiph.common.enums.status.order.RestaurantFormStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RestaurantFormUpdate {
    private Integer id;
    private String masterialName;
    private String formCode;
    private RestaurantFormStatus status;
}
