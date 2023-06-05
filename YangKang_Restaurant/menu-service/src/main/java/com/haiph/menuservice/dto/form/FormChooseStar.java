package com.haiph.menuservice.dto.form;

import com.haiph.common.enums.status.restaurantService.RestaurantStar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormChooseStar {
    private Integer id;
    private RestaurantStar star;
}
