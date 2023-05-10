package com.haiph.menuservice.repository;

import com.haiph.menuservice.entity.Payment;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query(nativeQuery = true, value = "" +
            "SELECT p.* FROM yangkang_data.payment p\n" +
            "JOIN yangkang_data.`order` o \n" +
            "ON p.order_id = o.id\n" +
            "WHERE o.id = ?1")
    List<Payment> findAllByOrderId(Integer OrderId);

    Payment findByPaymentCode(String paymentCode);

    @Query(value = "SELECT pa.* FROM yangkang_data.payment pa \n" +
            "LEFT JOIN yangkang_data.discount di on\n" +
            "pa.discount_id = di.id\n" +
            "WHERE :createDate >= :startDate and :createDate <= :endDate", nativeQuery = true)
    List<Payment> findPayMentByDiscount(@Param("createDate") LocalDate createDate,
                                  @Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate);

    @Query(nativeQuery = true, value = "SELECT * FROM `payment` where id in ?1")
    List<Payment> findListId(List<Integer> ids);
}
