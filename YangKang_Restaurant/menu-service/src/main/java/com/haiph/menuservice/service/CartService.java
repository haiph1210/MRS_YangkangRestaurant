package com.haiph.menuservice.service;

import com.haiph.menuservice.dto.request.CartRequest;
import com.haiph.menuservice.dto.response.CartResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {
    Page<CartResponse> findAll(Pageable pageable);

    CartResponse findById(Integer id);

    String create(CartRequest request);

    String update(Integer id, CartRequest request);

    String delete(Integer id);

    Integer totalCart();
}
