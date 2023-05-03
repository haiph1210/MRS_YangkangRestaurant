package com.haiph.userservice.feignClient;

import com.haiph.common.dto.response.APIResponse;
import com.haiph.userservice.model.request.email.SendMail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mail-service")
public interface MailController {
    @PostMapping("api/email/sendSimpleMail")
    APIResponse<String> sendMail(@RequestBody SendMail sendMail) ;
}
