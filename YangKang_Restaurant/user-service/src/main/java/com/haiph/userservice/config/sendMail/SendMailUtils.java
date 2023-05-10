package com.haiph.userservice.config.sendMail;

import com.haiph.common.email.SendMail;
import com.haiph.common.formEmail.ActivePerson;
import com.haiph.userservice.entity.User;
import com.haiph.userservice.feign.EmailController;
import com.haiph.userservice.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class SendMailUtils {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailController emailController;



    public String sendMailActive(String email) {
        Optional<User> person = userRepository.findByEmail(email);
        if (person.isPresent()) {
            String confirm = ActivePerson.CONFIRM + person.get().getUserCode();
            StringBuilder message = new StringBuilder();
            message.append(confirm);
            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "\n" +
                    "<style>\n" +
                    "    html{\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        box-sizing: border-box;\n" +
                    "        margin: 0px;\n" +
                    "        padding: 0px;\n" +
                    "        width: 1200px;\n" +
                    "    }\n" +
                    "    body{\n" +
                    "    }\n" +
                    "    .heading{\n" +
                    "        padding: 2rem 0rem 1.5rem 10rem;\n" +
                    "        font-size: 22px;\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        font-family: Arial, Helvetica, sans-serif;\n" +
                    "        color: brown;\n" +
                    "    }\n" +
                    "    .content{\n" +
                    "        padding: 2rem 0rem 0rem 10rem ;\n" +
                    "        font-size: 18px;\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        font-family: Arial, Helvetica, sans-serif;\n" +
                    "        color: brown;\n" +
                    "    }\n" +
                    "   \n" +
                    "</style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ\" crossorigin=\"anonymous\">\n" +
                    "    <div class=\"heading\">" + ActivePerson.SUBJECT + "</div>\n" +
                    "    <div class=\"content\"><p>" + ActivePerson.MESSAGE + "</p>\n" +
                    "            <br> \n" +
                    "        <p>" + message + "</p>\n" +
                    " <br>" +
                    " <br>" +
                    " <br>" +
                    " <br>" +
                    " <br>" +
                    " <br>" +
                    " <br>" +
                    " <br>" +
                    "    </div>\n" +
                    "            \n" +
                    "</body>\n" +
                    "</html>";
            emailController.sendMail(SendMail.build(email, ActivePerson.SUBJECT, html));
            return "Send mail to person: " + person.get().getUsername() + " success";
        } else
            return "Send mail to person: " + person.get().getUserCode() + " fail";
    }
}
