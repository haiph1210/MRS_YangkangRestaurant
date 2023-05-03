package com.haiph.menuservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Combo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String name;
    private Double price;
    private String description;
    @ElementCollection
    @CollectionTable(name = "combo_img_urls", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "img_url")
    private List<String> imgUrl;
    @ElementCollection
    @CollectionTable(name = "combo_menu_ids", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "menu_id")
    private List<Integer> menuIds;

    public Combo(String name, Double price, String description, List<String> imgUrl, List<Integer> menuIds) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
        this.menuIds = menuIds;
    }
}
