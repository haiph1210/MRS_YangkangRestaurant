package com.haiph.menuservice.dto.response;

import com.haiph.common.status.combo.NumberOfPeople;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
public class ComboResponse {
    private Integer id;
    private String name;
    private Double price;
    private NumberOfPeople numberOfPeople;
    private String description;
    private List<String> imgUrl;
    private List<MenuDTO> menus;
    @Data
    @AllArgsConstructor(staticName = "build")
    @NoArgsConstructor
    public static class MenuDTO{
        private String name;
        private String description;
    }
}
