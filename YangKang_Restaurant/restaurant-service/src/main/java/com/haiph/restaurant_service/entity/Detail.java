package com.haiph.restaurant_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor()
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "info_id",referencedColumnName = "id")
    private Info info;
    private String name;
    @CreationTimestamp
    private LocalDate createdDate;
    @OneToMany(mappedBy = "detail", cascade = CascadeType.ALL)
    private List<Masterial> masterials;

    public Detail(Integer id, Info info, String name) {
        this.id = id;
        this.info = info;
        this.name = name;
    }

    public Detail(Integer id) {
        this.id = id;
    }

    public List<Masterial> getMasterials() {
        return masterials;
    }

    public void setMasterials(List<Masterial> masterials) {
        this.masterials = masterials;
    }
}
