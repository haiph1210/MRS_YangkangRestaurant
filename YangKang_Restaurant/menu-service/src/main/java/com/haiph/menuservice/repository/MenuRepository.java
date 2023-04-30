package com.haiph.menuservice.repository;

import com.haiph.menuservice.entity.Combo;
import com.haiph.menuservice.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Integer> {
    List<Menu> findByName(String name);
    List<Menu> findByPrice(Double price);

    @Query(nativeQuery = true,
            value = "SELECT * FROM menu " +
                    "WHERE " +
                    "(?1 IS NULL OR id = ?1 OR name LIKE ?1)" +
                    "AND (?2 IS NULL OR price >= ?2) " +
                    "AND (?3 IS NULL OR price <= ?3) " +
                    "AND (?4 IS NULL OR img_url like ?4) " +
                    "AND (?5 IS NULL OR description like ?5 ) "
    )
    List<Menu> findWithForm(String menuNameOrId,Double minPrice,Double maxPrice,String imgUrl,String description);
    @Query(nativeQuery = true, value = ("SELECT * FROM menu WHERE id IN ?1"))

    List<Menu> findByListId(List<Integer> ids);
}
