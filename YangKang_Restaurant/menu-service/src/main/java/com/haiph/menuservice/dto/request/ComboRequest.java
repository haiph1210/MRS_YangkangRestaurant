package com.haiph.menuservice.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@Builder
public class ComboRequest {
    private String name;
    private String description;
    private List<MultipartFile> imgUrl;
    private List<Integer> menuIds;
}
