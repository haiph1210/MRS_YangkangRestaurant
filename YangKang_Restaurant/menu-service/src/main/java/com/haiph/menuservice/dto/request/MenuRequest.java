package com.haiph.menuservice.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@Builder
public class MenuRequest {
    private String name;
    private Double price;
    private Integer amount;
    private List<MultipartFile> imgUrl;
    private String description;
    private Integer comboId;
}
