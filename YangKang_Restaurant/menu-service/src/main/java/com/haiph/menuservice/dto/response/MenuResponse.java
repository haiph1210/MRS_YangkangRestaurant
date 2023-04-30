package com.haiph.menuservice.dto.response;

import lombok.*;

@Data
@AllArgsConstructor(staticName = "build")
@Builder
public class MenuResponse {
    private Integer id;
    private String name;
    private Double price;
    private String imgUrl;
    private String description;

    public MenuResponse(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }


}
