package com.haiph.restaurant_service.entity;

import com.haiph.common.enums.status.restaurantService.RestaurantFormStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "restaurant_form")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RestaurantForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String masterialName;
    @Column(unique = true)
    private String formCode;
    @Enumerated(EnumType.ORDINAL)
    private RestaurantFormStatus status;
    @CreationTimestamp
    private LocalDate createdDate;

    public RestaurantForm(String masterialName, String formCode, RestaurantFormStatus status) {
        this.masterialName = masterialName;
        this.formCode = formCode;
        this.status = status;
    }
}
