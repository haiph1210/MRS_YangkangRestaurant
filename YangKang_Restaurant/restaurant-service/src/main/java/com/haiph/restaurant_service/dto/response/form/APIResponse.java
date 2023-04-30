package com.haiph.restaurant_service.dto.response.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class APIResponse {
    private String responseCode;
    private String responseMessage;
    private List<MasterialDTO> responseData;
}
