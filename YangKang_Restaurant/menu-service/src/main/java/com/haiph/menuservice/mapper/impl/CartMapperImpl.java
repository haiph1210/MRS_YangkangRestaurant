package com.haiph.menuservice.mapper.impl;

import com.haiph.menuservice.dto.request.CartRequest;
import com.haiph.menuservice.dto.response.CartResponse;
import com.haiph.menuservice.dto.response.MenuResponse;
import com.haiph.menuservice.entity.Cart;
import com.haiph.menuservice.mapper.CartMapper;
import com.haiph.menuservice.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Component
public class CartMapperImpl implements CartMapper {
    @Autowired
    private MenuService menuService;



    private CartResponse.Response getResponse(String code) {
        String[] arrCode = code.split("-");
        String checkCode = "";
        CartResponse.Response responseCart = new CartResponse.Response();

        for (int i = 0; i < arrCode.length; i++) {
            checkCode = arrCode[0];
        }
        if (checkCode.equals("MENU")) {
            MenuResponse response = menuService.findByCode(code);
            responseCart = CartResponse.Response.build(
                    response.getId(),
                    response.getName(),
                    response.getPrice(),
                    response.getImgUrl(),
                    response.getDescription()
            );
        } else if (checkCode.equals("COMBO")) {
            // chưa tạo logic
        }
        return responseCart;
    }

    @Override
    public CartResponse CartMapCartResponse(Cart cart) {
        CartResponse cartResponse = CartResponse.
                build(cart.getId(),
                        cart.getCode(),
                        getResponse(cart.getCode()),
                        getResponse(cart.getCode()).getPrice(),
                        cart.getAmount(),
                        cart.getInitPrice(),
                        cart.getCreateDate()
                );
        return cartResponse;
    }


    @Override
    public List<CartResponse> ListCartMapListCartResponse(List<Cart> cart) {
        List<CartResponse> cartResponses = new ArrayList<>();
        for (Cart cart1 : cart) {
            CartResponse cartResponse = CartResponse.
                    build(cart1.getId(),
                            cart1.getCode(),
                            getResponse(cart1.getCode()),
                            getResponse(cart1.getCode()).getPrice(),
                            cart1.getAmount(),
                            cart1.getInitPrice(),
                            cart1.getCreateDate()
                    );
            cartResponses.add(cartResponse);
        }
        return cartResponses;
    }

    @Override
    public Cart cartRequestMapCartCreate(CartRequest request) {
        Cart cart = new Cart(
                request.getCode(),
                getResponse(request.getCode()).getPrice(),
                request.getAmount()
                );
        return cart;
    }

    @Override
    public Cart cartRequestMapCartUpdate(Integer id, CartRequest request) {
            Cart cart = Cart.build(
                    id,
                    request.getCode(),
                    getResponse(request.getCode()).getPrice(),
                    request.getAmount(),
                    totalPrice(getResponse(request.getCode()).getPrice(), request.getAmount()),
                    LocalDate.now()
            );
        return cart;
    }
    private Double totalPrice(Double price,Integer amount) {
        Double totalPrice = price * amount;
        return totalPrice;
    }
  }
