package com.haiph.employeeservice.dto.request;

import com.haiph.common.enums.status.emplService.empl.empl.Source;
import com.haiph.common.enums.status.emplService.empl.empl.WorkingDay;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class TimeSheetingDayRequest {
    private String personCode;
    private LocalDate dateCheck;
    private Integer positionId;
    private WorkingDay day;
    private Source source;
    private String note;
}
