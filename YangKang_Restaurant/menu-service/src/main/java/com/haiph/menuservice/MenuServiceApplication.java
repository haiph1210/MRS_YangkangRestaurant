package com.haiph.menuservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = "com.haiph")
@EnableFeignClients
@EnableDiscoveryClient
public class MenuServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MenuServiceApplication.class, args);
    }
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOrigins("http://localhost:3000") // Nguồn gốc của frontend "http://127.0.0.1:3000" Reactjs
                            .allowedMethods("HEAD","GET", "POST", "PUT", "DELETE") // Phương thức được phép
                            .allowedHeaders("*") // Các header được phép
                            .allowCredentials(true); // Cho phép gửi cookie
                }
            };
        }
}
