package com.haiph.restaurant_service.dto.request.detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class DetailCreateRequest {
    private Integer infoId;
    private String name;
}
