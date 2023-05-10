package com.haiph.emailservice.service;

import com.haiph.emailservice.entity.SendMail;

public interface MailService {
    String sendMailVersion2(String emailRevice, String subject, String message);

    String sendMail(SendMail sendMail);

    String sendMailWithAttachment(SendMail sendMail);
}
