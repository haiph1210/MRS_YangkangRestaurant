package com.haiph.menuservice.dto.response.restApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class PersonResponse {
    private String userCode;
    private String fullName;
    private String status;
    private String email;
}
