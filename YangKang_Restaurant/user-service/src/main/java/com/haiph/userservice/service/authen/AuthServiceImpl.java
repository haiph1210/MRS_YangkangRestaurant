package com.haiph.userservice.service.authen;

import com.haiph.common.dto.response.Response;
import com.haiph.common.enums.status.personService.person.Active;
import com.haiph.common.exception.CommonException;
import com.haiph.userservice.config.sendMail.SendMailUtils;
import com.haiph.userservice.dto.request.UserRequest;
import com.haiph.userservice.dto.request.sercurity.LoginRequest;
import com.haiph.userservice.dto.response.sercurity.TokenRespone;
import com.haiph.userservice.entity.User;
import com.haiph.userservice.repository.UserRepository;
import com.haiph.userservice.service.UserService;
import com.haiph.userservice.service.impl.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements com.haiph.userservice.service.AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SendMailUtils sendMail;


    @Override
    public TokenRespone login(LoginRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        System.out.println("alo alo");
        if (!user.isPresent()) {
            throw new CommonException(Response.PARAM_NOT_VALID, "username: " + request.getUsername() + " isn't exists");
        }
        String token = "";
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            if (authentication.isAuthenticated()) {
                token = generateToken(request.getUsername());
            } else throw new CommonException(Response.ACCESS_DENIED, "Invalid Access");
            if (checkActive(request.getUsername()) == false) {
                throw new CommonException(Response.ACCESS_DENIED, "USER NOT ACTIVE");
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = token;
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserPrinciple genpesoncode = (UserPrinciple) authentication.getPrincipal();
            String userCode = genpesoncode.getUserCode();
            User user1 = genpesoncode.getUser();
            return TokenRespone.build(
                    jwtToken,
                    userDetails.getUsername(),
                    userCode,
                    userDetails.getAuthorities().toString(),
                    user1
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Username Password Is Valid");

        } catch (UsernameNotFoundException exception) {
            System.out.println("exception = " + exception);
            throw new CommonException(Response.ACCESS_DENIED, exception.getMessage());
        }
    }

    @Override
    public String register(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CommonException(Response.PARAM_INVALID, "Username Exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CommonException(Response.PARAM_INVALID, "Email Exists");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.create(request);
        sendMail.sendMailActive(request.getEmail());
        return "Create Success";
    }

    @Override
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    @Override
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    private boolean checkActive(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.get().getStatus().equals(Active.NOT_ACTIVE)) {
            return false;
        }
        return true;
    }
}
