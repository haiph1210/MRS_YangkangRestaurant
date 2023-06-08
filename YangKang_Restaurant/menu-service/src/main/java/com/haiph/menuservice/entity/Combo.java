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
    private String code;
    private Double price;
    private String description;
    @Column(length = 10000)
    private String imgUrl;
    @ElementCollection
    @CollectionTable(name = "combo_menu_ids", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "menu_id")
    private List<Integer> menuIds;

    public Combo(String name, String code, Double price, String description, String imgUrl, List<Integer> menuIds) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
        this.menuIds = menuIds;
    }
}
