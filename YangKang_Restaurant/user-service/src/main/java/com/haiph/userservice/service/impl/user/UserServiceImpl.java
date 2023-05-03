package com.haiph.userservice.service.impl.user;

import com.haiph.common.enums.status.personService.person.Active;
import com.haiph.userservice.entity.person.Person;
import com.haiph.userservice.entity.person.User;

import com.haiph.userservice.model.request.user.UserRequest;
import com.haiph.userservice.model.response.dto.UserResponse;
import com.haiph.userservice.repository.person.PersonRepository;
import com.haiph.userservice.repository.person.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements com.haiph.userservice.service.UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<User> findAll() {
        List<User> userFind = userRepository.findAll();
        return userFind;
    }


    @Override
    public Page<UserResponse> findAllPage(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        List<User> users = page.getContent();
        List<UserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            UserResponse dto = new UserResponse();
            if (user!= null) {
                dto.setPersonCode(user.getPersonCode());
                dto.setEmail(user.getEmail());
                dto.setPhoneNumber(user.getPhoneNumber());
                dto.setAddress(user.getAddress());
                dto.setGender(user.getGender());
                dto.setStatus(user.getStatus());
                dto.setFullName(user.getFullName());
                dto.setRole(user.getRole());
                dto.setCreateDate(user.getCreatedDate());
                dto.setImgUrl(user.getImgUrl());
                dto.setUserType(user.getUserType());
                dto.setCountLogin(user.getCountLogin());
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos,pageable, page.getTotalElements());
    }

    @Override
    public UserResponse findById(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        UserResponse dto = new UserResponse();
        if (user!= null) {
            dto.setPersonCode(user.getPersonCode());
            dto.setEmail(user.getEmail());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setAddress(user.getAddress());
            dto.setGender(user.getGender());
            dto.setFullName(user.getFullName());
            dto.setRole(user.getRole());
            dto.setStatus(user.getStatus());
            dto.setCreateDate(user.getCreatedDate());
            dto.setImgUrl(user.getImgUrl());
            dto.setUserType(user.getUserType());
            dto.setCountLogin(user.getCountLogin());
        }
        return dto;
    }

    public UserResponse findByPersonCode(String personCode) {
        User user = userRepository.findByPersonCode(personCode).orElse(null);
        UserResponse dto = new UserResponse();
        if (user!= null) {
            dto.setPersonCode(user.getPersonCode());
            dto.setEmail(user.getEmail());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setAddress(user.getAddress());
            dto.setGender(user.getGender());
            dto.setStatus(user.getStatus());
            dto.setFullName(user.getFullName());
            dto.setRole(user.getRole());
            dto.setCreateDate(user.getCreatedDate());
            dto.setImgUrl(user.getImgUrl());
            dto.setUserType(user.getUserType());
            dto.setCountLogin(user.getCountLogin());
        }
        return dto;
    }

    @Override
    public UserResponse findByUsername(String username) {

        User user = userRepository.findByUsername(username).orElse(null);
        UserResponse dto = new UserResponse();
        if (user!= null) {
            dto.setPersonCode(user.getPersonCode());
            dto.setEmail(user.getEmail());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setAddress(user.getAddress());
            dto.setGender(user.getGender());
            dto.setStatus(user.getStatus());
            dto.setFullName(user.getFullName());
            dto.setRole(user.getRole());
            dto.setCreateDate(user.getCreatedDate());
            dto.setImgUrl(user.getImgUrl());
            dto.setUserType(user.getUserType());
            dto.setCountLogin(user.getCountLogin());
        }
        return dto;
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        UserResponse dto = new UserResponse();
        if (user!= null) {
            dto.setPersonCode(user.getPersonCode());
            dto.setEmail(user.getEmail());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setAddress(user.getAddress());
            dto.setGender(user.getGender());
            dto.setStatus(user.getStatus());
            dto.setFullName(user.getFullName());
            dto.setRole(user.getRole());
            dto.setCreateDate(user.getCreatedDate());
            dto.setImgUrl(user.getImgUrl());
            dto.setUserType(user.getUserType());
            dto.setCountLogin(user.getCountLogin());
        }
        return dto;
    }

    @Override
    public String create(UserRequest userCreate) {
        User user = mapper.map(userCreate, User.class);
        String fullName = userCreate.getFirstName() + " " + userCreate.getLastName();
        user.setPersonCode(generatePersonCode(fullName));
        user.setStatus(Active.NOT_ACTIVE);
        userRepository.save(user);
        return "create Success";
    }

    @Override
    public String update(UUID id ,UserRequest userUpdate) {
        UserResponse response  = findById(id);
        if (response != null) {
            User user = mapper.map(userUpdate, User.class);
            String fullName = userUpdate.getFirstName() + " " + userUpdate.getLastName();
            user.setPersonCode(generatePersonCode(fullName));
            user.setStatus(Active.NOT_ACTIVE);
             userRepository.save(user);
             return "Update Success";
        }
        return "Update fail";
    }

    @Override
    public void deleteById(UUID userId) {
        userRepository.deleteById(userId);
    }


    private static final int MAX_ATTEMPTS = 100;

    private String generatePersonCode(String fullName) {
        String temp = Normalizer.normalize(fullName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String output = pattern.matcher(temp).replaceAll("").replace('đ', 'd').replace('Đ', 'D');
        String[] newFullName = output.split(" ");
        String newPersonCode = "";
        int number = 1;
        if (newFullName.length > 0) {
            String lastName = newFullName[newFullName.length - 1].toUpperCase();
            String firstName = newFullName[0].substring(0, 2).toUpperCase();
            newPersonCode = lastName + firstName;
            int attempts = 0;
            while (attempts < MAX_ATTEMPTS) {
                Optional<Person> person = personRepository.findByPersonCode(newPersonCode + "-" + number);
                if (!person.isPresent()) {
                    newPersonCode = newPersonCode + "-" + number;
                    break;
                }
                number++;
                attempts++;
            }
        }
        return newPersonCode;
    }
}
