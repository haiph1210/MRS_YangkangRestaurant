package com.haiph.userservice.entity.person;

import com.haiph.common.enums.status.personService.empl.PositionEmpl;
import com.haiph.common.enums.status.personService.empl.Salary;
import com.haiph.common.enums.status.personService.person.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Employee extends Person {
    @Enumerated(EnumType.ORDINAL)
    private PositionEmpl position;
    private Float salary;
    private String cmnd;

    public Float getSalary() {
        float salaryEmployee = 0f;
        switch (this.position) {
            case CHEF:
                salaryEmployee = Salary.CHEF;
                break;
            case SERVICE:
                salaryEmployee = Salary.SERVICE;
                break;
            case SERVE:
                salaryEmployee = Salary.SERVE;
                break;
            case MANAGER:
                salaryEmployee = Salary.MANAGER;
                break;
        }
        return salaryEmployee;
    }

    @PrePersist
    public void prePersit() {
        if (this.role == null) {
            this.role = Role.EMPLOYEE;
        }
    }


}
