package com.haiph.userservice.entity;

import com.haiph.common.enums.status.personService.person.Active;
import com.haiph.common.enums.status.personService.person.Gender;
import com.haiph.common.enums.status.personService.person.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.id.factory.spi.GeneratorDefinitionResolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String userCode;
    private String firstName;
    private String lastName;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String address;
    private LocalDate birthDay;
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    @Enumerated(EnumType.ORDINAL)
    private Active status;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdDate;

    public User(String username, String password, String userCode, String firstName, String lastName, String email, String address, LocalDate birthDay, Gender gender) {
        this.username = username;
        this.password = password;
        this.userCode = userCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public User(String username, String password, String userCode, String firstName, String lastName, String address, LocalDate birthDay,Gender gender) {
        this.username = username;
        this.password = password;
        this.userCode = userCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    @PrePersist()
    public void perPersitst() {
        if (role == null) {
            this.role = Role.USER;
        }
        if (createdDate == null) {
            this.createdDate = LocalDateTime.now();
        }
        if (fullName == null) {
            this.fullName = this.firstName + " " + this.lastName;
        }
        if (status == null) {
            this.status = Active.NOT_ACTIVE;
        }
    }
}
