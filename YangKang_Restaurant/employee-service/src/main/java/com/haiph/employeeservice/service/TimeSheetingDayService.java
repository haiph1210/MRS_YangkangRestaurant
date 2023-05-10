package com.haiph.employeeservice.service;

import com.haiph.employeeservice.dto.request.TimeSheetingDayRequest;
import com.haiph.employeeservice.dto.response.TimeSheetingDayResponse;
import com.haiph.employeeservice.entity.EmployeeId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TimeSheetingDayService {
    List<TimeSheetingDayResponse> findAll();

    Page<TimeSheetingDayResponse> findAll(Pageable pageable);

    TimeSheetingDayResponse findById(EmployeeId id);

    String create(TimeSheetingDayRequest request);

    String update( TimeSheetingDayRequest request);

    String delete(EmployeeId id);
}
