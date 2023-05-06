package com.haiph.employeeservice.dto.request;

import com.haiph.common.enums.status.emplService.empl.position.PositionEmpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class PositionRequest {
    private PositionEmpl positionEmpl;
}
