package com.haiph.userservice.service.impl.user;

import com.haiph.common.dto.response.Response;
import com.haiph.common.enums.status.personService.person.Active;
import com.haiph.common.exception.CommonException;

import com.haiph.userservice.entity.person.Person;
import com.haiph.userservice.model.response.PersonResponse;
import com.haiph.userservice.repository.person.PersonRepository;
import com.haiph.userservice.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ModelMapper mapper;


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Person> person = personRepository.findByUsername(username);
////        return person.map(CustomerUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//        Optional<Person> account = personRepository.findByUsername(username);
//        System.out.println("load user: ");
//        System.out.println(account);
//        if (!account.isPresent()) throw new UsernameNotFoundException(username);
//
//        if (account.get().getRole() != null) {
//            return new User(
//                    account.get().getUsername(),
//                    account.get().getPassword(),
//                    AuthorityUtils.createAuthorityList(account.get().getRole().toString())
//            );
//        } else {
//            return new User(
//                    account.get().getUsername(),
//                    account.get().getPassword(),
//                    AuthorityUtils.createAuthorityList("EMPOYEE")
//            );
//        }
//    }

    @Override
    public Page<PersonResponse> findAllPage(Pageable pageable) {
        Page<Person> page = personRepository.findAll(pageable);
        List<Person> people = page.getContent();
        List<PersonResponse> dtos = new ArrayList<>();

        for (Person person : people) {

            PersonResponse dto = new PersonResponse();
            if (person != null) {
                dto.setPersonCode(person.getPersonCode());
                dto.setEmail(person.getEmail());
                dto.setPhoneNumber(person.getPhoneNumber());
                dto.setAddress(person.getAddress());
                dto.setGender(person.getGender());
                dto.setFullName(person.getFullName());
                dto.setRole(person.getRole());
                dto.setStatus(person.getStatus());
                dto.setCreateDate(person.getCreatedDate());
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Override
    public PersonResponse findById(UUID id) {
        Person person = personRepository.findById(id).orElse(null);
        PersonResponse dto = new PersonResponse();
        if (person != null) {
            dto.setPersonCode(person.getPersonCode());
            dto.setEmail(person.getEmail());
            dto.setPhoneNumber(person.getPhoneNumber());
            dto.setAddress(person.getAddress());
            dto.setGender(person.getGender());
            dto.setFullName(person.getFullName());
            dto.setRole(person.getRole());
            dto.setStatus(person.getStatus());
            dto.setCreateDate(person.getCreatedDate());
        }
        return dto;
    }

    @Override
    public PersonResponse findByPersonCode(String personCode) {
        Person person = personRepository.findByPersonCode(personCode).orElse(null);
        PersonResponse dto = new PersonResponse();
        if (person != null) {
            dto.setPersonCode(person.getPersonCode());
            dto.setEmail(person.getEmail());
            dto.setPhoneNumber(person.getPhoneNumber());
            dto.setAddress(person.getAddress());
            dto.setGender(person.getGender());
            dto.setFullName(person.getFullName());
            dto.setRole(person.getRole());
            dto.setStatus(person.getStatus());
            dto.setCreateDate(person.getCreatedDate());
        }
        return dto;
    }

    @Override
    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Override
    public String activePerson(String personCode) {
        Person person = personRepository.findByPersonCode(personCode).orElse(null);
        if (person != null) {
            person.setStatus(Active.ACTIVE);
            personRepository.save(person);
        } else
            throw new CommonException(Response.DATA_NOT_FOUND, "PersonCode cannot find: " + personCode);

        return "Person with ID: " + personCode + "active successfully ";
    }
}
