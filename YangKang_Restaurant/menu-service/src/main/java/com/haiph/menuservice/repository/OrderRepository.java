package com.haiph.menuservice.repository;

import com.haiph.menuservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
     Order findByOrderCode(String orderCode);
}
