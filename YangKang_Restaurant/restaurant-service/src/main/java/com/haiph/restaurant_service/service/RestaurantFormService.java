package com.haiph.restaurant_service.service;


import com.haiph.restaurant_service.dto.request.form.RestaurantFormCreate;
import com.haiph.restaurant_service.dto.request.form.RestaurantFormUpdate;
import com.haiph.restaurant_service.dto.response.form.RestaurantFormResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestaurantFormService {
    Page<RestaurantFormResponse> findAllPage(Pageable pageable);

    List<RestaurantFormResponse> findAll();

    RestaurantFormResponse findById(Integer id);

    RestaurantFormResponse findByFormCode(String formCode);

    String create(RestaurantFormCreate create);

    String update(RestaurantFormUpdate update);

    String deleteById(Integer id);

    String updateReady(List<Integer> id);

    String updatePending(List<Integer> id);

    String updateRefuse(List<Integer> id);

    String updateBooked(List<Integer> id);

    List<RestaurantFormResponse> findByListId(List<Integer> ids);
}
