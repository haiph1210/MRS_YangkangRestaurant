package com.haiph.restaurant_service.repository;

import com.haiph.restaurant_service.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoRepository extends JpaRepository<Info,Integer> {
    List<Info> findByName(String name);
}
