package com.haiph.emailservice.service;

import com.haiph.emailservice.entity.SendMail;

public interface MailService {
    String sendMail(SendMail sendMail);

    String sendMailWithAttachment(SendMail sendMail);
}
