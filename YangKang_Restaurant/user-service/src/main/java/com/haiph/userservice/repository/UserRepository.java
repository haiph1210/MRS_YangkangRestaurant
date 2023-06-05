package com.haiph.userservice.repository;

import com.haiph.userservice.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    @Query(nativeQuery = true,value = "" +
            "SELECT * FROM yangkang_admin_data.user\n" +
            "WHERE user_code like ?1")
    Optional<User> findByUserCode(String userCode);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "" +
            "update yangkang_admin_data.user u\n" +
            "set u.`password` = ?1 \n" +
            "where u.username = ?2")
    void changePassword(String password,String username);


    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "" +
            "update yangkang_admin_data.user u\n" +
            "set u.`img_url` = ?1 \n" +
            "where u.username = ?2")
    void updateAvatar(String imgUrl,String username);
}
