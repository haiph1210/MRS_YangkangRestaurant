package com.haiph.apigateway.filter;

import com.haiph.apigateway.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haiph.common.sercurity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class AuthenticationFillter extends AbstractGatewayFilterFactory<AuthenticationFillter.Config> {

    @Autowired
    private RouterValidator validator;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFillter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }
                String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }
                UserInfo userInfo = genUserInfoToTokenWithRedis(token);
                if (userInfo != null) {
                    try {
                        jwtUtil.validateToken(token);

                    } catch (Exception e) {
                        System.out.println("invalid access...!");
                        throw new RuntimeException("un authorized access to application");
                    }
                }

            }
            return chain.filter(exchange);
        });

    }

    public UserInfo genUserInfoToTokenWithRedis(String token) {
//        getToken use Token
        Object result = redisTemplate.opsForValue().get(token);
        if (result instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) result;
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(map, UserInfo.class);
        }
        return null;
    }


    public static class Config {

    }


}
