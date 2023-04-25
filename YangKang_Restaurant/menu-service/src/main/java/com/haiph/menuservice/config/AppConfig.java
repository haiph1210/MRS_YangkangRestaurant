package com.haiph.menuservice.config;

import com.haiph.common.redis.RedisConfig;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public RedisConfig redisConfig() {
        return new RedisConfig();
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
