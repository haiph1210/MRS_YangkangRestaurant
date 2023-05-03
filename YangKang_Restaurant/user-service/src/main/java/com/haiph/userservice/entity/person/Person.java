package com.haiph.userservice.entity.person;

import com.haiph.common.enums.status.personService.person.Active;
import com.haiph.common.enums.status.personService.person.Gender;
import com.haiph.common.enums.status.personService.person.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;

import java.util.UUID;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Data
public class Person {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column
    protected UUID id;
    @Column(unique = true, name = "person_code")
    protected String personCode;
    @Column(unique = true)
    protected String username;
    @Column
    protected String password;
    protected String imgUrl;
    @Column(unique = true)
    protected String email;
    @Column
    protected String phoneNumber;
    @Column
    protected String address;
    @Column
    @Enumerated(EnumType.ORDINAL)
    protected Gender gender;
    @Column
    @Enumerated(EnumType.ORDINAL)
    protected Active status;
    @Column(name = "first_name")
    protected String firstName;
    @Column(name = "last_name")
    protected String lastName;
    @Column
    @Formula("concat(first_name, ' ', last_name)")
    protected String fullName;
    @Column
    @Enumerated(EnumType.STRING)
    protected Role role;
    @Column
    @CreationTimestamp
    protected LocalDate createdDate;

    public String getFullName() {
        return this.fullName = this.firstName + " " + this.lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = this.firstName + " " + this.lastName;
    }


    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = Active.NOT_ACTIVE;
        }
        if (this.fullName == null) {
            this.fullName = this.firstName + " " + this.lastName;
        }
    }
}
