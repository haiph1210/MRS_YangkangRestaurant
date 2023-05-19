package com.haiph.apigateway.sercurity;//package com.haiph.apigateway.sercurity;
//
//import com.haiph.common.sercurity.UserInfo;
//import com.haiph.common.util.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.access.AuthorizationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Collections;
//
//public class JwtTokenRedisFilter extends OncePerRequestFilter {
//    private JwtConfig jwtConfig;
//    private RedisTemplate redisTemplate;
//
//    public JwtTokenRedisFilter(JwtConfig jwtConfig, RedisTemplate redisTemplate) {
//        this.jwtConfig = jwtConfig;
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String header = jwtConfig.getHeader();
//        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        String token = header.replace(jwtConfig.getPrefix(), "");
//        try {
//
//
//            if (!JwtUtil.validateToken(token)) {
//                throw new AuthorizationServiceException("Not authorization!!!");
//            }
//            UserInfo userInfo = getLoggedUserInRedis(token);
//            if (userInfo != null) {
////                redisTemplate.opsForValue().set(token, userInfo);
//                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userInfo, null, Collections.emptyList());
//                System.out.println("auth = " + auth);
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            } else {
//                SecurityContextHolder.clearContext();
//                throw new AuthorizationServiceException("Not authorization!!!");
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            SecurityContextHolder.clearContext();
//            throw new AuthorizationServiceException("Not authorization!!!");
//        }
//
//        filterChain.doFilter(request, response);
//    }
//    private UserInfo getLoggedUserInRedis(String token) {
//        return (UserInfo) redisTemplate.opsForValue().get(token);
//    }
//}
