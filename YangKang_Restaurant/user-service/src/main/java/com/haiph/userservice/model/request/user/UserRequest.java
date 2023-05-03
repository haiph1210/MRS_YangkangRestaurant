package com.haiph.userservice.model.request.user;

import com.haiph.common.enums.status.personService.person.Gender;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class UserRequest {
    @NotNull(message = "username shouldn't be null")
    private String username;
    @NotNull(message = "username shouldn't be null")
    private String password;
    @Email(message = "email invalid")
    private String email;
    @Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Invalid phone number")
    private String phoneNumber;
    private String address;
    @Pattern(regexp = "^(MALE|FEMALE)$", message = "Invalid gender")
    private Gender gender;
    private String firstName;
    private String lastName;
    private String imgUrl;

}
