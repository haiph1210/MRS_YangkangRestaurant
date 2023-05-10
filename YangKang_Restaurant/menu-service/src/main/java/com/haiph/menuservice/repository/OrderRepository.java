package com.haiph.menuservice.repository;

import com.haiph.menuservice.entity.Menu;
import com.haiph.menuservice.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
     Order findByOrderCode(String orderCode);
     @Transactional
     @Modifying
     @Query(nativeQuery = true,value = "UPDATE `order` SET `status` = 1 WHERE id =?1")
     int approvedOrder(Integer id) ;
     @Transactional
     @Modifying
     @Query(nativeQuery = true,value = "UPDATE `order` SET `status` = 2 WHERE id =?1")
     int refuseOrder(Integer id) ;

     @Query(nativeQuery = true,
             value = "SELECT * FROM `order` " +
             "WHERE " +
             "(?1 IS NULL OR id = ?1 OR order_code LIKE ?1)" +
             "AND (?2 IS NULL OR total_price >= ?2) " +
             "AND (?3 IS NULL OR total_price <= ?3) " +
             "AND (?4 IS NULL OR `hour` = ?4) " +
             "AND (?5 IS NULL OR type = ?5 ) " +
             "AND (?6 IS NULL OR status = ?6 ) "
     )
     List<Order> findByFormOrder(String orderCode,
                                 Double minTotalPrice,
                                 Double maxTotalPrice,
                                 LocalDateTime hour,
                                 Integer type,
                                 Integer status);
     @Query(nativeQuery = true, value = ("SELECT * FROM `order` WHERE id IN ?1"))

     List<Order> findByListId(List<Integer> ids);

     @Query(nativeQuery = true,value = "SELECT * FROM yangkang_data.order\n" +
             "WHERE `status` = 1")
     List<Order> findAllIfApproved() ;
}
