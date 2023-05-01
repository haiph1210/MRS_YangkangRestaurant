package com.haiph.restaurant_service.repository;

import com.haiph.restaurant_service.entity.RestaurantForm;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantFormRepository extends JpaRepository<RestaurantForm,Integer> {
    RestaurantForm findByFormCode(String formCode);
    @Query(nativeQuery = true, value = ("SELECT * FROM restaurant_form WHERE id IN ?1"))
    List<RestaurantForm> findByListID(List<Integer> ids);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "update yangkang_data.restaurant_form rf set rf.`status` = 0 WHERE id in ?1")
     void updateReady(List<Integer> id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "update yangkang_data.restaurant_form rf set rf.`status` = 1 WHERE id in ?1")
    void updatePending(List<Integer> ids);
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "update yangkang_data.restaurant_form rf set rf.`status` = 2 WHERE id in ?1")
    void updateRefuse(List<Integer> id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "update yangkang_data.restaurant_form rf set rf.`status` = 3 WHERE id in ?1")
    void updateBooked(List<Integer> id);
}
