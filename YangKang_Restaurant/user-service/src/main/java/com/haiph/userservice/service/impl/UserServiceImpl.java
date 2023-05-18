package com.haiph.userservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.enums.status.personService.person.Active;
import com.haiph.common.exception.CommonException;
import com.haiph.common.uploadfile.UploadFile;
import com.haiph.userservice.dto.request.UserRequest;
import com.haiph.userservice.dto.response.UserResponse;
import com.haiph.userservice.entity.User;
import com.haiph.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements com.haiph.userservice.service.UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UploadFile genfile;

    private Path path;

    public UserServiceImpl() {
        path = Paths.get("user-service/upload/img");
    }

    private String genUrlImage(MultipartFile file, String username, Path path) {
        return genfile.saveFile(file, username, path);
    }

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
                    user.getCreatedDate(),
                    user.getImgUrl());
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
                    user.getCreatedDate(),
                    user.getImgUrl());
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
                user.getCreatedDate(),
                user.getImgUrl());
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
                user.getCreatedDate(),
                user.getImgUrl());
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
                user.getCreatedDate(), user.getImgUrl());
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
                user.getCreatedDate(), user.getImgUrl());
        return response;
    }

    public String create(UserRequest request) {
//        Path path = Paths.get("user-service/upload/img");
        boolean existUsername = userRepository.existsByUsername(request.getUsername());
        boolean existEmail = userRepository.existsByEmail(request.getEmail());
        if (existUsername) {
            throw new CommonException(Response.PARAM_INVALID, "Username exists");
        }
        if (existEmail) {
            throw new CommonException(Response.PARAM_INVALID, "Email exists");
        }
        User user = new User(
                request.getUsername(),
                request.getPassword(),
                checkAndGen(request.getFirstName() + " " + request.getLastName()),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getAddress(),
                request.getBirthDay(),
                request.getGender(),
                genUrlImage(request.getImgUrl(), request.getUsername(), path)
        );
        userRepository.save(user);
        return "Create Success";
    }

    @Override
    public String Update(UUID id, UserRequest request) {
//        Path path = Paths.get("user-service/upload/img");
        UserResponse response = findById(id);
        if (response != null) {
            String oldFilePath = response.getImgUrl();
            try {
                Path oldFile = Paths.get(oldFilePath);
                Files.delete(oldFile);
            } catch (IOException e) {
                throw new CommonException(Response.PARAM_INVALID, "Cannot delete old image file: " + oldFilePath);
            }

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
                    response.getCreatedDate(),
                    genUrlImage(request.getImgUrl(), request.getUsername(), path)
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
        if (!user.isPresent())
            throw new CommonException(Response.ACCESS_DENIED, "Username: " + username + " isn't exists");
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

    @Override
    public String activeUserByUserCode(String userCode) {
        User response = userRepository.findByUserCode(userCode).
                orElseThrow(() -> {
                    throw new CommonException(Response.DATA_NOT_FOUND, "ACTIVE FAIL,USER CODE: " + userCode + " NOT EXISTS");
                });
        response.setStatus(Active.ACTIVE);
        userRepository.save(response);
        return "ACTIVE USERCODE: " + userCode + " SUCCESSFULLY";
    }

    @Override
    public String saveAdmin(List<User> users) {
        for (User user : users) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.saveAll(users);
        return "Save admin success";
    }
    @Override
    public byte[] readFileImg(String fileName) {
        return genfile.readFileContent(fileName, path);
    }

//    public Object getUploadFileAll() {
//        try {
//            List<String> urls = genfile.loadAll(path)
//                    .map(path -> {
//                        //convert fileName to url(send request "readDetailFile")
//                        String urlPath = MvcUriComponentsBuilder.fromMethodName(UserServiceImpl.class,
//                                "readFileImg", path.getFileName().toString()).build().toUri().toString();
//                        return urlPath;
//                    })
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        return null;
//    }
}
