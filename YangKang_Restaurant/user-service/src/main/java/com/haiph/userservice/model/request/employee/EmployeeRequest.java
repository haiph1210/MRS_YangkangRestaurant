package com.haiph.userservice.model.request.employee;

import com.haiph.common.enums.status.personService.empl.PositionEmpl;
import com.haiph.common.enums.status.personService.person.Gender;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class EmployeeRequest {
    @NotNull(message = "username shouldn't be null")
    private String username;
    @NotNull(message = "username shouldn't be null")
    private String password;
    @Email(message = "email invalid")
    private String email;
    @Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Invalid phone number")
    private String phoneNumber;
    private String imgUrl;
    private String address;
    @Pattern(regexp = "^(MALE|FEMALE)$", message = "Invalid gender")
    private Gender gender;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "^(SERVE|CHEF|MANAGER|SERVICE)$", message = "Invalid position")
    private PositionEmpl position;
    private String cmnd;

}
