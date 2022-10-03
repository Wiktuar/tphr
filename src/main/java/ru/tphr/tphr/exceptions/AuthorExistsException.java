package ru.tphr.tphr.exceptions;

// это исключение возникает тога, когда пользователь
// пытается зарегистрироваться с уже имеющимся в базе имеёлом

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such order")
public class AuthorExistsException extends RuntimeException{
//   public AuthorExistsException(String message) {
//        super(message);
//    }
}

//https://zetcode.com/springboot/responsestatus/
// здесь можно посмотреть, как во фримаркере сделать стра