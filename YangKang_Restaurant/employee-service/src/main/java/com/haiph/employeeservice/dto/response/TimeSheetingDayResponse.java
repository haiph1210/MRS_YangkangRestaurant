package com.haiph.employeeservice.dto.response;

import com.haiph.common.enums.status.emplService.empl.empl.Source;
import com.haiph.common.enums.status.emplService.empl.empl.WorkingDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class TimeSheetingDayResponse {
    private String personCode;
    private LocalDate dateCheck;
    private PositionRessponse positionResponse;
    private WorkingDay day;
    private Source source;
    private String note;
}
