package com.haiph.menuservice.dto.request;

import com.haiph.common.enums.status.restaurantService.RestaurantStar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class VottingRequest {
    private RestaurantStar star;
    private String userCode;
    private Integer menuId;
}
