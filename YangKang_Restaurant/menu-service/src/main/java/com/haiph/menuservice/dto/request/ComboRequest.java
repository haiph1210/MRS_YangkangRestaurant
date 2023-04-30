package com.haiph.menuservice.dto.request;

import com.haiph.common.enums.status.menu.NumberOfPeople;
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
