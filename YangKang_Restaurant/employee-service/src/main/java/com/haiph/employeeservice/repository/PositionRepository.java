package com.haiph.employeeservice.repository;

import com.haiph.employeeservice.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position,Integer> {
}
