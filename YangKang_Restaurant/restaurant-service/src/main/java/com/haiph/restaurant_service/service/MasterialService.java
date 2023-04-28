package com.haiph.restaurant_service.service;

import com.haiph.restaurant_service.dto.request.masterial.MasterialCreateRequest;
import com.haiph.restaurant_service.dto.request.masterial.MasterialUpdateRequest;
import com.haiph.restaurant_service.dto.response.MasterialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MasterialService {
    Page<MasterialResponse> findAllPage(Pageable pageable);

    MasterialResponse findById(Integer id);

    List<MasterialResponse> findByName(String name);

    String create(MasterialCreateRequest request);

    String update(MasterialUpdateRequest request);

    String deleteById(Integer id);
}
