package com.haiph.menuservice.entity;

import com.haiph.common.enums.status.restaurantService.RestaurantStar;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Data
public class Votting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RestaurantStar star;
    private String userCode;
    @ManyToOne
    @JoinColumn(name = "menu_id",referencedColumnName = "id")
    private Menu menu;

    public Votting(RestaurantStar star, String userCode, Menu menu) {
        this.star = star;
        this.userCode = userCode;
        this.menu = menu;
    }

    @PrePersist
    public void prePersit() {
        if (this.star == null) {
            this.star = RestaurantStar.FIVE;
        }
    }
}
