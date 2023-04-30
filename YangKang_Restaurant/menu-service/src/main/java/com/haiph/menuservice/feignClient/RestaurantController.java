package com.haiph.menuservice.feignClient;

import com.haiph.menuservice.dto.response.restApi.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "restaurant-service")
public interface RestaurantController {
    @GetMapping("/api/restaurant/form/findList/{ids}")
    public APIResponse findByListId(@PathVariable List<Integer> ids) ;
}
