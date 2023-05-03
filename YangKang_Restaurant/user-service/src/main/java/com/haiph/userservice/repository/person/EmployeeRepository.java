package com.haiph.userservice.repository.person;

import com.haiph.userservice.entity.person.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByPersonCode(String personCode);
}
