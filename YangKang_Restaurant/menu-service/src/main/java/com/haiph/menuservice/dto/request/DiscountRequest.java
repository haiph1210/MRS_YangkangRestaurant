package com.haiph.menuservice.dto.request;

import com.haiph.common.enums.status.menuService.discount.DiscountStatus;
import com.haiph.common.enums.status.menuService.discount.PercentDiscount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class DiscountRequest {
    private PercentDiscount percentDiscount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer infoId;
    private DiscountStatus status;
}
