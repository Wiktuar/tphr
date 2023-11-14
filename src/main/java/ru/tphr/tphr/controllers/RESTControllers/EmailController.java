package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tphr.tphr.services.MailSenderService;

@RestController
public class EmailController {

    private MailSenderService mailSenderService;

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

//    @GetMapping("/email")
//    public String sendEmail() throws Exception {
//        mailSenderService.sendEmail("Viktor", "Wiktuar");
//        return  "Email Sent..!";
//    }
}
