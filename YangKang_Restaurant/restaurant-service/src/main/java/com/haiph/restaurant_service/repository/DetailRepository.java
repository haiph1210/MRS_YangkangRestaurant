package com.haiph.restaurant_service.repository;

import com.haiph.restaurant_service.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<Detail,Integer> {
    Detail findByName(String name);
}
