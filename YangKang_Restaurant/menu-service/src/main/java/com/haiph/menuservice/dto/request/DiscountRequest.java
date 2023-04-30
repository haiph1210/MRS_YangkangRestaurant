package com.haiph.menuservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class DiscountRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer infoId;
    private Integer status;
}
