package com.haiph.menuservice.dto.response;

import com.haiph.common.enums.status.restaurantService.RestaurantStar;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@Builder
public class MenuResponse {
    private Integer id;
    private String name;
    private Double price;
    private String imgUrl;
    private String description;
    private Double totalStar;
    private String totalStarInTotalUser;
    private List<Votting> vottings;
    @Data
    @AllArgsConstructor(staticName = "build")
    @Builder
    public static class Votting {
        private RestaurantStar star;
        private String fullName;
    }
}
