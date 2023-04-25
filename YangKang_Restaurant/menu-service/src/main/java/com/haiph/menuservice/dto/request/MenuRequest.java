package com.haiph.menuservice.dto.request;

import lombok.*;

@Data
@AllArgsConstructor(staticName = "build")
@Builder
public class MenuRequest {
    private String name;
    private Double price;
    private String imgUrl;
    private String description;
    private Integer comboId;
}
