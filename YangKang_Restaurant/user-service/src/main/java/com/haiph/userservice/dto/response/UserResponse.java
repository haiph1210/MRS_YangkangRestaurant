package com.haiph.userservice.dto.response;

import com.haiph.common.enums.status.personService.person.Active;
import com.haiph.common.enums.status.personService.person.Gender;
import com.haiph.common.enums.status.personService.person.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String userCode;
    private String fullName;
    private String email;
    private String address;
    private LocalDate birthDay;
    private Gender gender;
    private Active status;
    private Role role;
    private LocalDateTime createdDate;
    private String imgUrl;
}
