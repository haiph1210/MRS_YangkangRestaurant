package com.haiph.employeeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
@Data
@AllArgsConstructor(staticName = "buid")
@NoArgsConstructor
public class EmployeeId implements Serializable {
    private String personCode;
    private LocalDate dateCheck;
}
