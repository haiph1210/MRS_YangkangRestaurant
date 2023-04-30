package com.haiph.menuservice.dto.response;

import com.haiph.common.enums.status.menuService.discount.DiscountStatus;
import com.haiph.menuservice.dto.response.restApi.InfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class DiscountResponse {
    private Integer id;
    private String discountCode;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private InfoResponse info;
    private DiscountStatus status;
}
