package com.haiph.menuservice.mapper;

import com.haiph.menuservice.dto.request.CartRequest;
import com.haiph.menuservice.dto.response.CartResponse;
import com.haiph.menuservice.entity.Cart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    CartResponse CartMapCartResponse(Cart cart);

    List<CartResponse> ListCartMapListCartResponse(List<Cart> cart);

    Cart cartRequestMapCartCreate(CartRequest request);

    Cart cartRequestMapCartUpdate(Integer id, CartRequest request);
}
