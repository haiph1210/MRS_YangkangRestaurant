package com.haiph.userservice.repository.person;

import com.haiph.userservice.entity.person.Person;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByPersonCode(String personCode);

    Optional<Person> findByUsername(String username);

    Optional<Person> findByEmail(String email);

    boolean existsByUsernameAndEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "Update person set `status` = 1 where  person_code = ?1")
    void updateActive(String personCode);
}
