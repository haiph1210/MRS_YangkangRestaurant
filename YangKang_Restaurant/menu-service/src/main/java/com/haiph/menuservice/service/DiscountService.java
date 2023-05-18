package com.haiph.menuservice.service;

import com.haiph.menuservice.dto.request.DiscountRequest;
import com.haiph.menuservice.dto.response.DiscountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscountService {
    Page<DiscountResponse> findAll(Pageable pageable);

    DiscountResponse findById(Integer id);

    DiscountResponse findByDiscountCode(String discountCode);

    String create(DiscountRequest request);

    String update(Integer id, DiscountRequest request);

    String delete(Integer id);

    String gennaterateQrService(Integer id);
}
