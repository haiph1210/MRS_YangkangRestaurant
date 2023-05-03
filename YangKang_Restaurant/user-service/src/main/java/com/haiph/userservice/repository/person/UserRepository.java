package com.haiph.userservice.repository.person;

import com.haiph.userservice.entity.person.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPersonCode(String personCode);
    Optional<User> findByEmail(String email);
}