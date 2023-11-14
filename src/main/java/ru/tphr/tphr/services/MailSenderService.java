package ru.tphr.tphr.services;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailSenderService {

    private JavaMailSender mailSender;

//  Это конфигурационный бин вримаркера. Он создается автоматически автоконфигурацией
//  freemarker.template.Configuration
    private Configuration configuration;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setConfiguration(Configuration configuration){
        this.configuration = configuration;
    }

    @Value("${spring.mail.username}")
    private String email;

//  метод отправки простого сообщения
    @Async
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(email);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    @Async
    public void sendEmail(String email, String name){
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try{
            helper.setSubject("Регистрация на сайте tphr.ru");
            helper.setTo(email);
            String emailContent = getEmailContent(name);
            helper.setText(emailContent, true);
        } catch (MessagingException ex){
            System.out.println("Ошибка при указании темы письма, адреса адресата или получения текста письма");
        }

        mailSender.send(mimeMessage);
    }

//  метод, который получает шаблон и внедряет в него данные из карты
    String getEmailContent(String name) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        try{
            configuration.getTemplate("email.ftl").process(model, stringWriter);
        } catch (IOException | TemplateException ex){
            System.out.println("Ошибка при формирования письма для подтверждения регистрации");
        }
        return stringWriter.getBuffer().toString();
    }
}
