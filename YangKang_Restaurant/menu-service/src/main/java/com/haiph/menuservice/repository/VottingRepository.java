package com.haiph.menuservice.repository;

import com.haiph.menuservice.entity.Votting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VottingRepository extends JpaRepository<Votting,Integer> {
    @Query(nativeQuery = true,value = "SELECT count(star) FROM yangkang_data.votting WHERE menu_id = ?1")
    Integer findTotalVote(Integer menuId);
}
