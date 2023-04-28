package com.haiph.restaurant_service.repository;

import com.haiph.restaurant_service.entity.RestaurantForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantFormRepository extends JpaRepository<RestaurantForm,Integer> {
    RestaurantForm findByFormCode(String formCode);
}
