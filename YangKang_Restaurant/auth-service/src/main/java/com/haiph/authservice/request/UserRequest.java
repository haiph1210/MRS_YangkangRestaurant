package com.haiph.authservice.request;

import com.haiph.common.enums.status.personService.person.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private LocalDate birthDay;
    private Gender gender;
    private MultipartFile imgUrl;

    public UserRequest(String username, String password, String firstName, String lastName, String email, String address, LocalDate birthDay, Gender gender) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.birthDay = birthDay;
        this.gender = gender;
    }
}
