package com.haiph.employeeservice.feignClient;

import com.haiph.common.dto.response.APIResponse;
import com.haiph.employeeservice.dto.response.RESTAPI.PersonResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface PersonController {
    @GetMapping("/api/person/findPerCode/{personCode}")
    APIResponse<PersonResponse> findByPersonCode(@PathVariable("personCode") String personCode);
}
