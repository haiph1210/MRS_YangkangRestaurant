package com.haiph.menuservice.repository;

import com.haiph.menuservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    @Query(nativeQuery = true,value = "Select count(id) from cart where user_code = ?1")
    Integer totalCart (String userCode);
    @Query(nativeQuery = true,value =
            ("Select * from cart where user_code = ?1"))
    List<Cart> findByUserCode(String userCode);
    @Query(nativeQuery = true,value = "" +
            "SELECT c.* FROM yangkang_data.cart c\n" +
            "left join yangkang_data.menu m\n" +
            "on m.`code` = c.`code`\n" +
            "where c.user_code like ?1\n" +
            ";")
    List<Cart> findAllLeftJoinCartAndMenu(String userCode);
}
