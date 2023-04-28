package com.haiph.restaurant_service.dto.response;

import com.haiph.common.enums.status.restaurant.RestaurantStar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class InfoResponse {
    private Integer id;
    private String name;
    private String hostline;
    private String phoneNumber;
    private String email;
    private String address;
    private String description;
    private RestaurantStar star;
    private LocalDate createdAt;
    private String imgUrl;
    private List<DetailResponse> details;
//    @Data
//    @AllArgsConstructor(staticName = "build")
//    @NoArgsConstructor
//    public static class DetailDTO {
//        private Integer id;
//        private String name;
//    }
}
