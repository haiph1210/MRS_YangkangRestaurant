package com.haiph.restaurant_service;

import com.haiph.common.enums.status.restaurantService.RestaurantFormStatus;
import com.haiph.restaurant_service.entity.RestaurantForm;
import com.haiph.restaurant_service.repository.RestaurantFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.time.LocalDate;

@SpringBootApplication(scanBasePackages = "com.haiph")
@EnableDiscoveryClient
public class RestaurantServiceApplication implements CommandLineRunner {
    @Autowired
    private RestaurantFormRepository formRepository;
    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
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
