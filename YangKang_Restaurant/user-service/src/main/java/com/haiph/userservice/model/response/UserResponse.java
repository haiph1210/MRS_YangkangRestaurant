package com.haiph.userservice.model.response;

import com.haiph.common.enums.status.personService.person.Active;
import com.haiph.common.enums.status.personService.person.Gender;
import com.haiph.common.enums.status.personService.person.Role;
import com.haiph.common.enums.status.personService.user.UserType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserResponse {
    private String personCode;
    private String fullName;
    private String imgUrl;
    private String email;
    private String phoneNumber;
    private String address;
    private Gender gender;
    private Active status;
    private Role role;
    private LocalDate createDate;
    private UserType userType;
    private Integer countLogin;
}
