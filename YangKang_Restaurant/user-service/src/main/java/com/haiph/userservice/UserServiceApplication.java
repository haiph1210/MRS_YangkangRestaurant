package com.haiph.userservice;

import com.haiph.common.enums.status.personService.person.Active;
import com.haiph.common.enums.status.personService.person.Role;
import com.haiph.common.enums.status.restaurantService.RestaurantFormStatus;
import com.haiph.userservice.dto.response.UserResponse;
import com.haiph.userservice.entity.User;
import com.haiph.userservice.repository.UserRepository;
import com.haiph.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication(scanBasePackages = "com.haiph")
@EnableDiscoveryClient
@EnableFeignClients
public class UserServiceApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
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
        Optional<User> userResponse1 = userRepository.findByUserCode("ADMIN");
        Optional<User> userResponse2 = userRepository.findByUserCode("ADMIN+1");
        if (userResponse1 == null && userResponse2 == null) {
            User admin1 = new User(
                    "admin",
                    "admin123",
                    "ADMIN",
                    "haiph12102001@gmail.com",
                    Active.ACTIVE,
                    Role.ADMIN);
            User admin2 = new User(
                    "haiph1210",
                    "admin123",
                    "ADMIN+1",
                    "haiph2001@gmail.com",
                    Active.ACTIVE,
                    Role.ADMIN);
            List<User> listAdmin = Arrays.asList(admin1,admin2);
            userService.saveAdmin(listAdmin);
        }
        System.out.println("Đã có thông tin admin");

    }
}
