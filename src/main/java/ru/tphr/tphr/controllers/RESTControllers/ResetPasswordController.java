package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.exceptions.AuthorExistsException;
import ru.tphr.tphr.services.AuthorService;

import javax.servlet.http.HttpServletRequest;

// метод получения страницы "resetpassword.ftl" находится в классе LoginController.java
@RestController
@RequestMapping("/reset")
public class ResetPasswordController {
    private AuthorService authorService;

    @Autowired
    public void setService(AuthorService authorService) {
        this.authorService = authorService;
    }

//  метод, высылающий письмо на электронную почту со ссылкой на напоминание пароля
//  в качестве аргумента получает адрес почты, на который нужно отправить ссылку
    @PostMapping("/remindPassword")
    public HttpStatus resetPassword(@RequestParam String email,
                                    HttpServletRequest request){
        Author author = authorService.getAuthorByEmail(email);
        if (author == null)
            throw new AuthorExistsException();
        authorService.createResetPassToken(author, request);
        return HttpStatus.OK;
    }

    @PostMapping("/password")
    public HttpStatus updatePassword(@RequestParam("id") long id,
                                 @RequestParam("password") String password){
        authorService.updateAuthorById(password, id);
        return HttpStatus.OK;
    }
}
