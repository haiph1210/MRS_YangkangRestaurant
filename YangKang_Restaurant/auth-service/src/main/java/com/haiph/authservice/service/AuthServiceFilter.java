//package com.haiph.authservice.service;
//
//
//import com.fasterxml.jackson.core.exc.StreamReadException;
//import com.fasterxml.jackson.databind.DatabindException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.haiph.authservice.feignclient.UserController;
//import com.haiph.authservice.request.AuthRequest;
//import com.haiph.common.dto.response.APIResponse;
//import com.haiph.common.sercurity.TokenResponse;
//import com.haiph.common.sercurity.UserInfo;
//import com.haiph.common.util.JwtUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.SneakyThrows;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Collections;
//
//public class AuthServiceFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final UserController userController;
//
//
//    public AuthServiceFilter(UserController userController) {
//        super();
//        this.userController = userController;
//        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("http://localhost:8080/api/authen", "POST"));
//    }
//
//    @SneakyThrows
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//        try {
//            UserCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
//            APIResponse<TokenResponse> apiResponse = userController.RESPONSE(AuthRequest.build(credentials.getUsername(), credentials.getPassword()));
//            if (apiResponse.getResponseData() != null) {
//                TokenResponse tokenResponse = apiResponse.getResponseData();
//                UserInfo userInfo = UserInfo.builder()
//                        .username(tokenResponse.getUsername())
//                        .userCode(tokenResponse.getUserCode())
//                        .email(
//                                tokenResponse.getUser().getEmail())
//                        .build();
//                String token = JwtUtil.generateToken(tokenResponse.getUsername());
//                userInfo.setToken(token);
//                sendResponse(apiResponse, response);
//                if (apiResponse.getResponseData() == null) return null;
//                return new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), Collections.emptyList());
//            }
//        } catch (StreamReadException e) {
//            throw new RuntimeException(e);
//        } catch (DatabindException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//
//    }
//
//    private void sendResponse(APIResponse<TokenResponse> responseBody, HttpServletResponse response) throws IOException {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json");
//        ObjectMapper om = new ObjectMapper();
//        String objString = om.writeValueAsString(responseBody);
//        PrintWriter out = response.getWriter();
//        out.print(objString);
//    }
//
//    private static class UserCredentials {
//        private String username;
//        private String password;
//
//        public String getUsername() {
//            return username;
//        }
//
//        public void setUsername(String username) {
//            this.username = username;
//        }
//
//        public String getPassword() {
//            return password;
//        }
//
//        public void setPassword(String password) {
//            this.password = password;
//        }
//    }
//}
