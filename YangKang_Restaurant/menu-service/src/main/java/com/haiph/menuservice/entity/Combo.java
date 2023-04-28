package com.haiph.menuservice.entity;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.common.enums.status.combo.NumberOfPeople;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String name;
    private Double price;
    @Enumerated(value = EnumType.STRING)
    private NumberOfPeople numberOfPeople;
    private String description;
    @ElementCollection
    @CollectionTable(name = "combo_img_urls", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "img_url")
    private List<String> imgUrl;
    @ElementCollection
    @CollectionTable(name = "menu_ids", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "menu_id")
    private List<Integer> menuIds;


    @PrePersist
    public void perPersist() {
        switch (numberOfPeople) {
            case FOUR:
                price = 500000d;
                break;
            case EIGHT:
                price = 1000000d;
                break;
            case TEN:
                price = 1200000d;
                break;
            case TWIFTEEN:
                price = 1500000d;
                break;
            case TWIFTY:
                price = 2500000d;
                break;
            case FULL:
                price = 10000000d;
                break;
            default:
                throw new CommonException(Response.DATA_NOT_FOUND, "Cannot find COMBO with people you choose");
        }
    }
}
