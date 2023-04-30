package com.haiph.menuservice.service;

import com.haiph.menuservice.dto.request.OrderRequest;
import com.haiph.menuservice.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderResponse> findAllPage(Pageable pageable);

    String create(OrderRequest request);
}
