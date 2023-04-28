package com.haiph.restaurant_service.repository;

import com.haiph.restaurant_service.dto.response.MasterialResponse;
import com.haiph.restaurant_service.entity.Masterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MasterialRepository extends JpaRepository<Masterial,Integer> {
    @Query(nativeQuery = true ,
            value = "SELECT * FROM yangkang_data.masterial WHERE `name` like ?1" )
    List<Masterial> findByName(String name);
}
