package com.haiph.menuservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class CartResponse {
    private Integer id;
    private String code;
    private Response response;
    private Double price;
    private Integer amount;
    private Double initPrice;
    private LocalDate createDate;
    @Data
    @AllArgsConstructor(staticName = "build")
    @NoArgsConstructor
    public static class Response {
        private Integer id;
        private String name;
        private Double price;
        private String imgUrl;
        private String description;
    }
}
