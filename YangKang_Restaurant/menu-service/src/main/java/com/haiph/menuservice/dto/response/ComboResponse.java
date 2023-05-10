package com.haiph.menuservice.dto.response;

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
    private String description;
    private List<String> imgUrl;
    private List<MenuResponse> menus;

}
