package com.haiph.userservice.service;

import com.haiph.userservice.entity.person.Employee;
import com.haiph.userservice.model.request.employee.EmployeeRequest;
import com.haiph.userservice.model.response.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    List<Employee> findAll();

    Page<EmployeeResponse> findAllPage(Pageable pageable);

    EmployeeResponse findById(UUID id);

    String create(EmployeeRequest create);

    String update(UUID id,EmployeeRequest update);

    String deleteById(UUID id);
}
