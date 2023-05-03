package com.haiph.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class APIResponseList<T> {
        private String responseCode;
        private String responseMessage;
        private List<T> responseData;
}
