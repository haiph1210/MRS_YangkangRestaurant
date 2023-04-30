package com.haiph.restaurant_service.repository;

import com.haiph.restaurant_service.entity.RestaurantForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantFormRepository extends JpaRepository<RestaurantForm,Integer> {
    RestaurantForm findByFormCode(String formCode);
    @Query(nativeQuery = true, value = ("SELECT * FROM restaurant_form WHERE id IN ?1"))
    List<RestaurantForm> findByListID(List<Integer> ids);
}
