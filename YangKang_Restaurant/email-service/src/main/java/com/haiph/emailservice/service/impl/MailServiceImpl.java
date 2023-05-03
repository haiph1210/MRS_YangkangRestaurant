package com.haiph.emailservice.service.impl;

import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;
import com.haiph.emailservice.entity.SendMail;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import java.io.File;
@Component
public class MailServiceImpl implements com.haiph.emailservice.service.MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendMail(SendMail sendMail) {
        try {
            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                messageHelper.setFrom(sender);
                messageHelper.setTo(sendMail.getEmailRevice());
                messageHelper.setSubject(sendMail.getSubject());
                messageHelper.setText(sendMail.getMessage(), true);
            };
            javaMailSender.send(messagePreparator);
            return "SendMail Success";
        }catch (CommonException exception){
            throw new CommonException(Response.SYSTEM_ERROR,exception.getMessage());
        }

    }

    @Override
    public String sendMailWithAttachment(SendMail sendMail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(sendMail.getEmailRevice());
            mimeMessageHelper.setSubject(sendMail.getSubject());
            mimeMessageHelper.setText(sendMail.getMessage());

            FileSystemResource file = new FileSystemResource(new File(sendMail.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);
            javaMailSender.send(mimeMessage);
            return "Mail and Attachment Send Succesfully!!!";

        } catch (Exception exception) {
            return "Mail And Attachement Error!!!";
        }

    }

}
