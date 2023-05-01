package com.haiph.menuservice.service;

import com.haiph.menuservice.dto.form.SearchFormOrder;
import com.haiph.menuservice.dto.request.OrderRequest;
import com.haiph.menuservice.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<OrderResponse> findAllPage(Pageable pageable);

    OrderResponse findById(Integer id);

    OrderResponse findByOrderCode(String orderCode);

    String create(OrderRequest request);

    String update(Integer id, OrderRequest request);

    String delete(Integer id);

    String approvedOrder(Integer id);

    String refuseOrder(Integer id);

    List<OrderResponse> findFormOrder(SearchFormOrder formOrder);

    List<OrderResponse> findListId(List<Integer> ids);
}
