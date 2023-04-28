package com.haiph.restaurant_service.service;


import com.haiph.restaurant_service.dto.request.form.RestaurantFormCreate;
import com.haiph.restaurant_service.dto.request.form.RestaurantFormUpdate;
import com.haiph.restaurant_service.dto.response.form.RestaurantFormResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantFormService {
    Page<RestaurantFormResponse> findAllPage(Pageable pageable);

    RestaurantFormResponse findById(Integer id);

    RestaurantFormResponse findByFormCode(String formCode);

    String create(RestaurantFormCreate create);

    String update(RestaurantFormUpdate update);

    String deleteById(Integer id);
}
