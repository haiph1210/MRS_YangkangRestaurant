package com.haiph.menuservice.service;

import com.haiph.menuservice.dto.request.CartRequest;
import com.haiph.menuservice.dto.response.CartResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CartService {
    Map<String,List<CartResponse>> findUserCodeToListCart(String userCode);

    Page<CartResponse> findAll(Pageable pageable);

    CartResponse findById(Integer id);

    List<CartResponse> findByUserCode(String userCode);

    List<CartResponse> findByUserCodeV2(String userCode);

    String create(CartRequest request);

    String update(Integer id, CartRequest request);

    String delete(Integer id);

    Integer totalCart(String userCode);
}
