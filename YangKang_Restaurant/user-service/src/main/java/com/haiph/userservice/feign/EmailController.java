package com.haiph.userservice.feign;

import com.haiph.common.dto.response.APIResponse;
import com.haiph.common.email.SendMail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Component
@FeignClient(name = "mail-service")
public interface EmailController {
    @PostMapping("api/email/sendSimpleMail")
    APIResponse<String> sendMail(@RequestBody SendMail sendMail) ;
}