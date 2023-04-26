package com.haiph.restaurant_service.dto.request.Info;

import com.haiph.common.enums.status.restaurant.RestaurantStar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class InfoCreateRequest {
    private String name;
    private String hostline;
    private String phoneNumber;
    private String email;
    private String address;
    private String description;
    private RestaurantStar star;
    private LocalDate createdAt;
    private String imgUrl;
}
