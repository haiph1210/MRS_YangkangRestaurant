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
@Table(name = "user")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(unique = true,name = "username",nullable = false)
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(unique = true,name = "user_code")
    private String userCode;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "full_name")
    private String fullName;
    @Column(unique = true,name = "email",nullable = false)
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "birth_day")
    private LocalDate birthDay;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender")
    private Gender gender;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Active status;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "img_url")
    private String imgUrl;

    public User(String username, String password, String userCode, String email, Active status, Role role) {
        this.username = username;
        this.password = password;
        this.userCode = userCode;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    public User(String username, String password, String userCode, String firstName, String lastName, String email, String address, LocalDate birthDay, Gender gender,String imgUrl) {
        this.username = username;
        this.password = password;
        this.userCode = userCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.birthDay = birthDay;
        this.gender = gender;
        this.imgUrl = imgUrl;
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
