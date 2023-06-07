package com.haiph.menuservice.repository;

import com.haiph.menuservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    @Query(nativeQuery = true,value = "Select count(id) from cart")
    Integer totalCart ();
}
