package com.haiph.userservice.entity.person;

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
    private Float salary;
    private String cmnd;

    @PrePersist
    public void prePersit() {
        if (this.role == null) {
            this.role = Role.EMPLOYEE;
        }
    }


}
