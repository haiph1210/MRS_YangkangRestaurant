package com.haiph.restaurant_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class DetailResponse {
    private Integer id;
    private String infoName;
    private String name;
    private List<MasterialDTO> masterials;
    @Data
    @AllArgsConstructor(staticName = "build")
    @NoArgsConstructor
    public static class MasterialDTO {
        private String name;
        private Integer quantity;
        private Double initPrice;
    }
}
