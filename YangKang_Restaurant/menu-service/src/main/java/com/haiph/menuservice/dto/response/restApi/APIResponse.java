package com.haiph.menuservice.dto.response.restApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class APIResponse<T> {
    private String responseCode;
    private String responseMessage;
    private List<T> responseData;
}
