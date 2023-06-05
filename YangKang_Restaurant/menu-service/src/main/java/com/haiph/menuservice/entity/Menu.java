package com.haiph.menuservice.entity;

import com.haiph.common.enums.status.restaurantService.RestaurantStar;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Menu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String code;
    private Double price;
    @Column(name = "img_url", length = 10000)
    private String imgUrl;
    private String description;
    private Double initStar;
    private String totalStarInTotalUser;
    @OneToMany(mappedBy = "menu", cascade = {CascadeType.ALL})
    private List<Votting> vottings;

    public Menu(String name,String code, Double price, String imgUrl, String description) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    @PrePersist
    public void prePersit() {
        if (this.initStar == null) {
            this.initStar = 5.0d;
        }
        if (this.totalStarInTotalUser == null) {
            this.totalStarInTotalUser = initStar + "/0";
        }
    }


}
