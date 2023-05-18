package com.haiph.restaurant_service.service;

import com.haiph.restaurant_service.dto.request.Info.InfoCreateRequest;
import com.haiph.restaurant_service.dto.request.Info.InfoUpdateRequest;
import com.haiph.restaurant_service.dto.response.InfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InfoService {
    byte[] readFileImg(String fileName);

    Page<InfoResponse> findAllPage(Pageable pageable);

    InfoResponse findById(Integer id);

    InfoResponse findByName(String name);

    String create(InfoCreateRequest request);

    String update(InfoUpdateRequest request);

    String deleteById(Integer id);
}
