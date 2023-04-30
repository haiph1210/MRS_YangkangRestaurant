package com.haiph.menuservice.entity;

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
    private Double price;
    private String imgUrl;
    private String description;

    public Menu(String name, Double price, String imgUrl, String description) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.description = description;

    }
}
