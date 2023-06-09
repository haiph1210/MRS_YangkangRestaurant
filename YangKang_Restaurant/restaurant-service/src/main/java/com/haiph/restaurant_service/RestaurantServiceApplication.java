package com.haiph.restaurant_service;

import com.haiph.common.enums.status.restaurantService.RestaurantFormStatus;
import com.haiph.restaurant_service.entity.RestaurantForm;
import com.haiph.restaurant_service.repository.RestaurantFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;

@SpringBootApplication(scanBasePackages = "com.haiph")
@EnableDiscoveryClient
public class RestaurantServiceApplication implements CommandLineRunner {
    @Autowired
    private RestaurantFormRepository formRepository;
    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5502") // Nguồn gốc của frontend
                        .allowedMethods("HEAD","GET", "POST", "PUT", "DELETE") // Phương thức được phép
                        .allowedHeaders("*") // Các header được phép
                        .allowCredentials(true); // Cho phép gửi cookie
            }
        };
    }
    @Override
    public void run(String... args) throws Exception {
        RestaurantForm search = formRepository.findByFormCode("MVN") ;
        if (search == null) {
            RestaurantForm form = RestaurantForm.build(0,"Mang Về Nhà","MVN", RestaurantFormStatus.READY, LocalDate.ofEpochDay(2022-12-10));
            formRepository.save(form);
        }
    }

}
