package com.haiph.menuservice.dto.request;

import com.haiph.common.enums.status.menu.NumberOfPeople;
import com.haiph.common.enums.status.order.OrderStatus;
import com.haiph.common.enums.status.order.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class OrderRequest {
    private List<Integer> idMenus;
    private List<Integer> idCombos;
    private List<Integer> idForms;
    private NumberOfPeople people;
    private LocalDateTime hour;
    private String description;
    private OrderType type;
}
