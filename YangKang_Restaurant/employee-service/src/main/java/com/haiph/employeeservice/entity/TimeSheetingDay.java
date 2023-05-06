package com.haiph.employeeservice.entity;

import com.haiph.common.enums.status.emplService.empl.empl.Source;
import com.haiph.common.enums.status.emplService.empl.empl.WorkingDay;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@AllArgsConstructor(staticName = "build")
@IdClass(EmployeeId.class)
@NoArgsConstructor

public class TimeSheetingDay {
    @Id
    private String personCode;
    @Id
    private LocalDate dateCheck;
    private Integer positionId;
    private WorkingDay day;
    private Source source;
    private String note;
}
