package com.haiph.employeeservice.entity;

import com.haiph.common.enums.status.emplService.empl.position.PositionEmpl;
import com.haiph.common.enums.status.emplService.empl.position.Salary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private PositionEmpl position;
    private Double salary;

    public Position(PositionEmpl position, Double salary) {
        this.position = position;
        this.salary = salary;
    }
}

