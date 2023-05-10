package com.haiph.employeeservice.dto.response;

import com.haiph.common.enums.status.emplService.empl.position.PositionEmpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class PositionRessponse {
    private Integer id;
    private PositionEmpl position;
    private Double salary;
}
