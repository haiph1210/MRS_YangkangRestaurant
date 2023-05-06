package com.haiph.employeeservice.service;

import com.haiph.employeeservice.dto.request.PositionRequest;
import com.haiph.employeeservice.dto.response.PositionRessponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PositionService {
    List<PositionRessponse> findAll();

    Page<PositionRessponse> findAll(Pageable pageable);

    PositionRessponse findById(Integer id);

    String create(PositionRequest request);

    String update(Integer id, PositionRequest request);

    String delete(Integer id);
}
