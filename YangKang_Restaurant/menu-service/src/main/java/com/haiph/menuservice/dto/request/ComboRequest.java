package com.haiph.menuservice.dto.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@Builder
public class ComboRequest {
    private String name;
    private String description;
    private List<String> imgUrl;
    private List<Integer> menuIds;
}
