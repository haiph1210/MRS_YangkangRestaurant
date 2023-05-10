package com.haiph.userservice.repository;

import com.haiph.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
