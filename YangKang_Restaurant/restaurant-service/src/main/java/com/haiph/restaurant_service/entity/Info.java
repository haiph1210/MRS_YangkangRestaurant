package com.haiph.restaurant_service.entity;

import com.haiph.common.enums.status.restaurantService.RestaurantStar;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "info")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String name;
    @Column
    private String hostline;
    @Column
    private String phoneNumber;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String description;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private RestaurantStar star;
    @CreationTimestamp
    private LocalDate createdAt;
    @Column
    private String imgUrl;

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    @OneToMany(mappedBy = "info",cascade = CascadeType.ALL)
    private List<Detail> details;

    public Info(Integer id) {
        this.id = id;
    }

    public Info(Integer id, String name, String hostline, String phoneNumber, String email, String address, String description, RestaurantStar star, LocalDate createdAt, String imgUrl) {
        this.id = id;
        this.name = name;
        this.hostline = hostline;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.description = description;
        this.star = star;
        this.createdAt = createdAt;
        this.imgUrl = imgUrl;
    }

    public Info( String name, String hostline, String phoneNumber, String email, String address, String description, RestaurantStar star, LocalDate createdAt, String imgUrl) {
        this.name = name;
        this.hostline = hostline;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.description = description;
        this.star = star;
        this.createdAt = createdAt;
        this.imgUrl = imgUrl;
    }
}
