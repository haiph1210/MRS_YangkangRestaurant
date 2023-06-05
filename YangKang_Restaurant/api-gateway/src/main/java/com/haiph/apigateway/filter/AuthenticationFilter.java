package com.haiph.apigateway.filter;

import com.haiph.apigateway.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.common.sercurity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouterValidator validator;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        CorsConfiguration corsConfiguration = config.getCorsConfiguration();
        return ((exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders responseHeaders = response.getHeaders();
            responseHeaders.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, corsConfiguration.getAllowedOrigins());
            responseHeaders.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, corsConfiguration.getAllowedMethods());
            responseHeaders.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, corsConfiguration.getAllowedHeaders());
            responseHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, String.valueOf(corsConfiguration.getAllowCredentials()));
            if (validator.isSecured.test(exchange.getRequest())) {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                if (!requestHeaders.containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new CommonException(Response.NOT_FOUND, "Cannot Find Header: Bearer Token");
                }

                String token = requestHeaders.getFirst(HttpHeaders.AUTHORIZATION);
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                UserInfo userInfo = genUserInfoFromTokenWithRedis(token);
                if (userInfo == null) {
                    throw new CommonException(Response.NOT_FOUND, "Invalid Token");
                }

                try {
                    if (!jwtUtil.validateToken(token)) {
                        throw new CommonException(Response.NOT_FOUND, "Token Isn't exists");
                    }
                } catch (CommonException e) {
                    System.out.println("Invalid access...!");
                    throw new CommonException(Response.NOT_FOUND, "Token expired");
                }
            }
            return chain.filter(exchange);
        });
    }

    public UserInfo genUserInfoFromTokenWithRedis(String token) {
        Object result = redisTemplate.opsForValue().get(token);
        if (result instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) result;
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(map, UserInfo.class);
        }
        return null;
    }



    public static class Config {
        private CorsConfiguration corsConfiguration;

        public Config() {
            corsConfiguration = new CorsConfiguration();
            // Thêm cấu hình CORS vào đây
            corsConfiguration.addAllowedOrigin("*");
            corsConfiguration.addAllowedMethod("*");
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.setAllowCredentials(true);
        }

        public CorsConfiguration getCorsConfiguration() {
            return corsConfiguration;
        }
    }
}
