package com.haiph.userservice.service;

import com.haiph.userservice.entity.person.Person;
import com.haiph.userservice.model.response.dto.PersonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PersonService{

    Page<PersonResponse> findAllPage(Pageable pageable);

    PersonResponse findById(UUID personId);

    PersonResponse findByPersonCode(String personCode);

    Optional<Person> findByUsername(String username);


    String activePerson(String personcode);
}
