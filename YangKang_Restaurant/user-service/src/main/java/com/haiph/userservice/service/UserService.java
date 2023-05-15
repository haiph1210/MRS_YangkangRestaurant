package com.haiph.userservice.service;

import com.haiph.userservice.dto.request.UserRequest;
import com.haiph.userservice.dto.response.UserResponse;
import com.haiph.userservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    List<UserResponse> findAll();

    Page<UserResponse> findAllPage(Pageable pageable);

    UserResponse findById(UUID id);

    UserResponse findByUsername(String username);

    UserResponse findByEmail(String email);

    UserResponse findByUserCode(String userCode);

    String create(UserRequest request);

    String Update(UUID id, UserRequest request);

    String delete(UUID id);

    String activeUserByUserCode(String userCode);

    String saveAdmin(List<User> users);
}
