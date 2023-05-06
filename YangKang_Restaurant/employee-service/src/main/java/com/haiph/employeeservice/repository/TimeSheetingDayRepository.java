package com.haiph.employeeservice.repository;

import com.haiph.employeeservice.entity.EmployeeId;
import com.haiph.employeeservice.entity.TimeSheetingDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSheetingDayRepository extends JpaRepository<TimeSheetingDay, EmployeeId> {
}
