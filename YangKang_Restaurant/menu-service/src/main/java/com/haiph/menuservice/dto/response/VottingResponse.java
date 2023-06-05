package com.haiph.menuservice.dto.response;

import com.haiph.common.enums.status.restaurantService.RestaurantStar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class VottingResponse {
    private Integer id;
    private RestaurantStar star;
    private String fullName;
    private String menuName;
}
