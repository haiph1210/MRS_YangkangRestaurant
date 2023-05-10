package com.haiph.menuservice.repository;

import com.haiph.menuservice.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    Discount findByDiscountCode(String discountCode);

    @Query(nativeQuery = true,
            value = "SELECT * FROM yangkang_data.discount\n" +
                    "WHERE (?1<= now() && ?2 >= now())")
    List<Discount> existsByStartDateAndEndDate(LocalDateTime startDate, LocalDateTime endDate);

    @Query(nativeQuery = true,
            value = "SELECT * FROM yangkang_data.discount " +
                    "WHERE " +
                    "(?1 IS NULL OR id = ?1 OR discount_code LIKE ?1)" +
                    "AND (?2 IS NULL OR start_date <= ?2) " +
                    "AND (?3 IS NULL OR end_date >= ?3) " +
                    "AND (?4 IS NULL OR info_id = ?4) " +
                    "AND (?5 IS NULL OR status = ?5 ) "
    )
    List<Discount> findByForm(String search, LocalDateTime startDate, LocalDateTime endDate, Integer infoId, Integer status);

}
