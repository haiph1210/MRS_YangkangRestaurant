package com.haiph.menuservice.entity;

import com.haiph.common.enums.status.menuService.discount.DiscountStatus;
import com.haiph.common.enums.status.menuService.discount.PercentDiscount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer infoId;
    private String discountCode;
    @Enumerated(EnumType.ORDINAL)
    private PercentDiscount percentDiscount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.ORDINAL)
    private DiscountStatus status;

    public Discount(Integer infoId, PercentDiscount percentDiscount, LocalDateTime startDate, LocalDateTime endDate) {
        this.infoId = infoId;
        this.percentDiscount = percentDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
