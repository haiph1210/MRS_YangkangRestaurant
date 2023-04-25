package com.haiph.menuservice.repository;

import com.haiph.common.status.combo.NumberOfPeople;
import com.haiph.menuservice.entity.Combo;
import com.haiph.menuservice.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ComboRepository extends JpaRepository<Combo,Integer> {
    @Query(nativeQuery = true,value = "SELECT * FROM yangkang_data.combo WHERE `name` = ?1")
    List<Combo> findByName(String name);
    List<Combo> findByPrice(Double price);

    @Query(nativeQuery = true,
            value = "SELECT * FROM combo " +
                    "WHERE " +
                    "(?1 IS NULL OR id like ?1 OR name LIKE ?1)" +
                    "AND (?2 IS NULL OR price >= ?2) " +
                    "AND (?3 IS NULL OR price <= ?3) " +
                    "AND (?4 IS NULL OR number_of_people like ?4) " +
                    "AND (?5 IS NULL OR description like ?5 ) " )
    List<Combo> findWithForm(String orderNameOrId, Double minPrice, Double maxPrice, String numberOfPeople, String description);


}
