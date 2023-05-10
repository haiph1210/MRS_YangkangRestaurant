package com.haiph.menuservice.repository;

import com.haiph.menuservice.entity.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComboRepository extends JpaRepository<Combo,Integer> {
    @Query(nativeQuery = true,value = "SELECT * FROM yangkang_data.combo WHERE `name` = ?1")
    List<Combo> findByName(String name);
    List<Combo> findByPrice(Double price);

    @Query(nativeQuery = true,
            value = "SELECT * FROM combo " +
                    "WHERE " +
                    "(?1 IS NULL OR id like ?1 OR name LIKE ?1)" +
                    "AND (?2 IS NULL OR price >= ?2) " +
                    "AND (?3 IS NULL OR price <= ?3) " )
    List<Combo> findWithForm(String orderNameOrId, Double minPrice, Double maxPrice);
    @Query(nativeQuery = true, value = ("SELECT * FROM combo WHERE id IN ?1"))

    List<Combo> findByListId(List<Integer> ids);
}
