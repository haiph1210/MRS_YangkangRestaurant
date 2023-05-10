package com.haiph.menuservice.feignClient;

import com.haiph.menuservice.dto.response.restApi.APIResponse;
import com.haiph.menuservice.dto.response.restApi.APIResponse2;
import com.haiph.menuservice.dto.response.restApi.InfoResponse;
import com.haiph.menuservice.dto.response.restApi.RestaurantFormResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "restaurant-service")
public interface RestaurantController {
    @GetMapping("/api/restaurant/form/findList/{ids}")
    public APIResponse<RestaurantFormResponse> findByListId(@PathVariable List<Integer> ids) ;
    @GetMapping("/api/restaurant/form/formCode")
    public APIResponse2<RestaurantFormResponse> findByFormCode(@PathParam("formCode") String formCode) ;
    @PutMapping("/api/restaurant/form/updateBooked/{id}")
    public APIResponse2<String> updateBooked(@PathVariable List<Integer> id);
    @PutMapping("/api/restaurant/form/updateReady/{id}")
    public APIResponse2<String> updateReady(@PathVariable List<Integer> id);

    @GetMapping("api/restaurant/info/findId/{id}")
    public APIResponse2<InfoResponse> findByInfoId(@PathVariable Integer id);
}
