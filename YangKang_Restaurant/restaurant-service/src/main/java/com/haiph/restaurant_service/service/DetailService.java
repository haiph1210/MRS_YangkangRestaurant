package com.haiph.restaurant_service.service;

import com.haiph.restaurant_service.dto.request.detail.DetailCreateRequest;
import com.haiph.restaurant_service.dto.request.detail.DetailUpdateRequest;
import com.haiph.restaurant_service.dto.response.DetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DetailService {
    Page<DetailResponse> findAllPage(Pageable pageable);

    DetailResponse findById(Integer id);

    DetailResponse findByName(String name);

    String create(DetailCreateRequest request);

    String update(DetailUpdateRequest request);

    String deleteById(Integer id);
}
