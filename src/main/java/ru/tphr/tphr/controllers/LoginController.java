package ru.tphr.tphr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.services.AuthorService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class LoginController {

    private AuthorService authorService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    // получение страницы регистрации автора
    @GetMapping
    public String getRegistrationPage(){
        return "registration";
    }

    //  метод получения страницы личного кабинета
    @GetMapping("/cabinet")
    public String getCabinet(Model model){
        Author author = new Author();
        author.setFirstName("Виктор");
        author.setLastName("Гусев");
        author.setEmail("wiktuar@yandex.ru");
        author.setPathToAvatar("Виктор");
        author.setVk("Виктор");
        author.setTg("Виктор");
        return "cabinet";
    }


    // активация аакаунта автора
    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable("code") String code){
//        boolean isActivated = authorService.activateUser(code);
//        if(isActivated) System.out.println("Пользователь успешно активирован");
//        else System.out.println("Активировать пользователя неполучилось");
        return "login";
    }
}


//старая версия загрузки аватара на случай, если новая лсомается
//    String data = img;
//    String base64Image = data.split(",")[1];
//    byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
//        try {
//                BufferedImage bImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
//                File outputfile = new File("C:\\Users\\wiktu\\Desktop\\tempFiles\\image.jpg");
//                ImageIO.write(bImg, "jpg", outputfile);
//                } catch (IOException e) {
//                e.printStackTrace();
//                }