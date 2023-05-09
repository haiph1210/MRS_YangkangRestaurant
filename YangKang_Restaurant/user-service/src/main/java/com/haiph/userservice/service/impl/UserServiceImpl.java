package com.haiph.userservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.userservice.dto.request.UserRequest;
import com.haiph.userservice.dto.response.UserResponse;
import com.haiph.userservice.entity.User;
import com.haiph.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse response = UserResponse.build(
                    user.getId(),
                    user.getUsername(),
                    user.getUserCode(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getBirthDay(),
                    user.getGender(),
                    user.getStatus(),
                    user.getRole(),
                    user.getCreatedDate());
            userResponses.add(response);
        }
        return userResponses;
    }

    @Override
    public Page<UserResponse> findAllPage(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        List<User> users = page.getContent();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse response = UserResponse.build(
                    user.getId(),
                    user.getUsername(),
                    user.getUserCode(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getBirthDay(),
                    user.getGender(),
                    user.getStatus(),
                    user.getRole(),
                    user.getCreatedDate());
            userResponses.add(response);
        }
        return new PageImpl<>(userResponses, pageable, page.getTotalElements());
    }

    @Override
    public UserResponse findById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "Cannot find ID: " + id);
        });
        UserResponse response = UserResponse.build(
                user.getId(),
                user.getUsername(),
                user.getUserCode(),
                user.getFullName(),
                user.getEmail(),
                user.getAddress(),
                user.getBirthDay(),
                user.getGender(),
                user.getStatus(),
                user.getRole(),
                user.getCreatedDate());
        return response;
    }

    @Override
    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "Cannot find username: " + username);
        });
        UserResponse response = UserResponse.build(
                user.getId(),
                user.getUsername(),
                user.getUserCode(),
                user.getFullName(),
                user.getEmail(),
                user.getAddress(),
                user.getBirthDay(),
                user.getGender(),
                user.getStatus(),
                user.getRole(),
                user.getCreatedDate());
        return response;
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "Cannot find email: " + email);
        });
        UserResponse response = UserResponse.build(user.getId(),
                user.getUsername(),
                user.getUserCode(),
                user.getFullName(),
                user.getEmail(),
                user.getAddress(),
                user.getBirthDay(),
                user.getGender(),
                user.getStatus(),
                user.getRole(),
                user.getCreatedDate());
        return response;
    }

    @Override
    public UserResponse findByUserCode(String userCode) {
        User user = userRepository.findByUserCode(userCode).orElseThrow(() -> {
            throw new CommonException(Response.NOT_FOUND, "Cannot find userCode: " + userCode);
        });
        UserResponse response = UserResponse.build(user.getId(),
                user.getUsername(),
                user.getUserCode(),
                user.getFullName(),
                user.getEmail(),
                user.getAddress(),
                user.getBirthDay(),
                user.getGender(),
                user.getStatus(),
                user.getRole(),
                user.getCreatedDate());
        return response;
    }

    @Override
    public String create(UserRequest request) {
        User user = new User(
                request.getUsername(),
                request.getPassword(),
                checkAndGen(request.getFirstName() + " " + request.getLastName()),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getAddress(),
                request.getBirthDay(),
                request.getGender()
        );
        userRepository.save(user);
        return "Create Success";
    }

    @Override
    public String Update(UUID id, UserRequest request) {
        UserResponse response = findById(id);
        if (response != null) {
            User user = User.build(
                    id,
                    response.getUsername(),
                    request.getPassword(),
                    checkAndGen(request.getFirstName() + " " + request.getLastName()),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getFirstName() + " " + request.getLastName(),
                    response.getEmail(),
                    request.getAddress(),
                    request.getBirthDay(),
                    request.getGender(),
                    response.getStatus(),
                    response.getRole(),
                    response.getCreatedDate()
            );
            userRepository.save(user);
            return "update success";
        }
        return "update fail";
    }

    @Override
    public String delete(UUID id) {
        UserResponse response = findById(id);
        if (response != null) {
            userRepository.deleteById(id);
            return "delete success";
        }
        return "delete fail";
    }


    public String genUserCode(String fullName) {
        // Chuẩn hóa và loại bỏ dấu tiếng Việt trong tên đầy đủ
        String temp = Normalizer.normalize(fullName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String output = pattern.matcher(temp).replaceAll("").replace('đ', 'd').replace('Đ', 'D');

        // Tách tên thành các từ riêng biệt
        String[] user = output.trim().split(" ");

        String userCode = "";
        String firstUser = user[user.length - 1];
        userCode += firstUser;

        for (int i = 0; i < user.length - 1; i++) {
            // Lấy chữ cái đầu tiên của các từ đầu
            String lastUser = user[i].substring(0, 1);
            userCode += lastUser;
        }
        return userCode.toUpperCase();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) throw new CommonException(Response.ACCESS_DENIED, "Username: " + username + " isn't exists");
        return new UserPrinciple(user.get());
    }

    private String checkAndGen(String fullName) {
        Integer number = 1;
        String userCode = genUserCode(fullName);
        String userCodeEnd = userCode;
        User user = userRepository.findByUserCode(userCode).orElse(null);
        while (user != null) {
            userCodeEnd = userCode + "-" + number;
            User user2 = userRepository.findByUserCode(userCodeEnd).orElse(null);
            if (user2 == null) {
                return userCodeEnd;
            }
            number++;

        }

        return userCodeEnd;


    }
}
