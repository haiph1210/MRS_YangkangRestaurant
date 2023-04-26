package com.haiph.restaurant_service.repository;

import com.haiph.restaurant_service.entity.Masterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterialRepository extends JpaRepository<Masterial,Integer> {
    List<Masterial> findByName(String name);
}
