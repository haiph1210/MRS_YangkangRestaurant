package com.haiph.menuservice.service;

import com.haiph.menuservice.dto.request.VottingRequest;
import com.haiph.menuservice.dto.response.VottingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VottingService {
    Page<VottingResponse> findAllPage(Pageable pageable);

    VottingResponse findById(Integer id);

    String create(VottingRequest request);

    String update(Integer id, VottingRequest request);

    String deleteById(Integer id);
}
