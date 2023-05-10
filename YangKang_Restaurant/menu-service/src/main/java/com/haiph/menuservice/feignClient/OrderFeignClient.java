package com.haiph.menuservice.feignClient;

import com.haiph.menuservice.dto.response.OrderResponse;
import com.haiph.menuservice.dto.response.restApi.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "menu-service")
public interface OrderFeignClient {
    @GetMapping("api/order/findListId/{ids}")
    public APIResponse<OrderResponse> findByListId(@PathVariable List<Integer> ids);
}
