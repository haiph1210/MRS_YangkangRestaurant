package com.haiph.userservice.model.response;

import com.haiph.common.enums.status.emplService.empl.position.PositionEmpl;
import com.haiph.common.enums.status.personService.person.Active;
import com.haiph.common.enums.status.personService.person.Gender;
import com.haiph.common.enums.status.personService.person.Role;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class EmployeeResponse {
    private String personCode;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Gender gender;
    private Active status;
    private Role role;
    private PositionEmpl position;
    private Float salary;
    private String cmnd;
    private LocalDate createdDate;
}
