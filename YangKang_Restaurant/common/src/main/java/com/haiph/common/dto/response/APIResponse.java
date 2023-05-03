package com.haiph.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class APIResponse<T> {
    private String responseCode;
    private String responseMessage;
    private T responseData;
}
