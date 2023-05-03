package com.haiph.menuservice.feignClient;

import com.haiph.menuservice.dto.response.restApi.APIResponse2;
import com.haiph.menuservice.dto.response.restApi.PersonResponse;
import com.haiph.menuservice.dto.response.restApi.RestaurantFormResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface PersonController {
    @GetMapping("/api/person/personCode")
    public APIResponse2<PersonResponse> findByPersonCode(@PathVariable("personCode") String personCode) ;
}
