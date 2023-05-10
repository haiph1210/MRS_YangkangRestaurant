package com.haiph.menuservice.feignClient;

import com.haiph.common.dto.response.APIResponse;
import com.haiph.common.email.SendMail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mail-service")
public interface MailController {
    @PostMapping("api/email/sendSimpleMail")
    APIResponse<String> sendMail(@RequestBody SendMail sendMail) ;
}
