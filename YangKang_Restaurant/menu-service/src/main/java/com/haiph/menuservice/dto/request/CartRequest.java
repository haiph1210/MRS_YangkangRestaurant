package com.haiph.menuservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private String userCode;
    private String code;
    private Integer amount;
}
