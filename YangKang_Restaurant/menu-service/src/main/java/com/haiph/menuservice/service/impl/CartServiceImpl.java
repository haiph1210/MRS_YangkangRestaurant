package com.haiph.menuservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.menuservice.dto.request.CartRequest;
import com.haiph.menuservice.dto.response.CartResponse;
import com.haiph.menuservice.entity.Cart;
import com.haiph.menuservice.entity.Combo;
import com.haiph.menuservice.entity.Menu;
import com.haiph.menuservice.mapper.CartMapper;
import com.haiph.menuservice.repository.CartRepository;
import com.haiph.menuservice.repository.ComboRepository;
import com.haiph.menuservice.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements com.haiph.menuservice.service.CartService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartMapper mapper;

    @Override
    public Map<String, List<CartResponse>> findUserCodeToListCart(String userCode) {
        Map<String, List<CartResponse>> userCodeAndCartMap = new HashMap<>();
        List<CartResponse> cartResponses = findByUserCode(userCode);
        if (!cartResponses.isEmpty()) {
            userCodeAndCartMap.put(userCode, cartResponses);
        }
        return userCodeAndCartMap;
    }

    @Override
    public Page<CartResponse> findAll(Pageable pageable) {
        Page<Cart> page = cartRepository.findAll(pageable);
        List<Cart> carts = page.getContent();
        List<CartResponse> responses = mapper.ListCartMapListCartResponse(carts);
        return new PageImpl<>(responses, pageable, page.getTotalElements());
    }


    @Override
    public CartResponse findById(Integer id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "cannot find Cart id: " + id);
        });
        CartResponse cartResponse = mapper.CartMapCartResponse(cart);
        return cartResponse;
    }

    @Override
    public List<CartResponse> findByUserCode(String userCode) {
        List<Cart> carts = cartRepository.findByUserCode(userCode);
        List<CartResponse> cartResponse = mapper.ListCartMapListCartResponse(carts);
        return cartResponse;
    }

    @Override
    public Map<String, List<CartResponse>> findByUserCodeV2(String userCode) {
        Map<String, List<CartResponse>> cartToUserCodeMap = new HashMap<>();
        List<Cart> carts = cartRepository.findAllLeftJoinCartAndMenu(userCode);
        List<Cart> newCart = checkCodeIsEmpty(carts);
        List<CartResponse> cartResponse = mapper.ListCartMapListCartResponse(newCart);
        cartToUserCodeMap.put(userCode, cartResponse);
        return cartToUserCodeMap;
    }


    private List<Cart> checkCodeIsEmpty(List<Cart> carts) {
        List<Cart> newCarts = new ArrayList<>();
        for (Cart cart : carts) {
            String code = cart.getCode();
            Integer id = cart.getId();
            Optional<Menu> menu = menuRepository.findByCode(code);
            Optional<Combo> combo = comboRepository.findByCode(code);
            if (!menu.isPresent() && !combo.isPresent()) {
                cartRepository.deleteById(id);
            } else {
                newCarts.add(cart);
            }
        }
        return newCarts;
    }

    @Override
    public String create(CartRequest request) {
        Cart cart = mapper.cartRequestMapCartCreate(request);
        cartRepository.save(cart);
        return "Create New Cart Success";
    }

    @Override
    public String update(Integer id, CartRequest request) {
        CartResponse cartResponse = findById(id);
        Cart cart = mapper.cartRequestMapCartUpdate(id, request);
        cartRepository.save(cart);
        return "Update Cart With ID: " + id + " Success";
    }

    @Override
    public String delete(Integer id) {
        CartResponse cartResponse = findById(id);
        cartRepository.deleteById(id);
        return "Delete Cart Success";
    }

    @Override
    public Integer totalCart(String userCode) {
        return cartRepository.totalCart(userCode);
    }
}
