package com.haiph.userservice.entity.person;

import com.haiph.common.enums.status.personService.person.Role;
import com.haiph.common.enums.status.personService.user.UserType;
import jakarta.persistence.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.ws.rs.DefaultValue;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class User extends Person {
    @Enumerated(EnumType.ORDINAL)
    private UserType userType;
    @DefaultValue("0")
    private Integer countLogin;

    @PrePersist
    public void prePersit() {
        if (this.role == null) {
            this.role = Role.USER;
        }
    }
}
