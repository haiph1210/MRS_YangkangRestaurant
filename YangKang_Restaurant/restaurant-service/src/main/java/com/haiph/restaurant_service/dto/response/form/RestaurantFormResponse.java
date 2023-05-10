package com.haiph.restaurant_service.dto.response.form;

import com.haiph.common.enums.status.restaurantService.RestaurantFormStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RestaurantFormResponse {
    private Integer id;
    private List<MasterialDTO> masterialName;
    private String formCode;
    private RestaurantFormStatus status;

}
