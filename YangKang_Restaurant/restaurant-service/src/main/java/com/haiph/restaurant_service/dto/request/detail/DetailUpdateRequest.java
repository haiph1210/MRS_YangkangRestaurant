package com.haiph.restaurant_service.dto.request.detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class DetailUpdateRequest {
    private Integer id;
    private Integer infoId;
    private String name;
}
