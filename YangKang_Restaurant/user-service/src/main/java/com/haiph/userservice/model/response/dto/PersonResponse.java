package com.haiph.userservice.model.response.dto;

import com.haiph.common.enums.status.personService.person.Active;
import com.haiph.common.enums.status.personService.person.Gender;
import com.haiph.common.enums.status.personService.person.Role;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class PersonResponse {
    private String personCode;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Gender gender;
    private Active status;
    private Role role;
    private LocalDate createDate;
}
