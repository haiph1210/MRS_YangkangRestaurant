package com.haiph.userservice.service;

import com.haiph.userservice.entity.person.User;
import com.haiph.userservice.model.request.user.UserRequest;
import com.haiph.userservice.model.response.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    Page<UserResponse> findAllPage(Pageable pageable);

    UserResponse findById(UUID userId);

    UserResponse findByUsername(String username);

    UserResponse findByEmail(String email);

    String create(UserRequest userCreate);

    String update(UUID id,UserRequest userUpdate);

    void deleteById(UUID userId);
}
