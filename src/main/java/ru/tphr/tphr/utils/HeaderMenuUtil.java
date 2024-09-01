package ru.tphr.tphr.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import ru.tphr.tphr.DTO.AuthorDTO;
import ru.tphr.tphr.services.AuthorService;

import java.security.Principal;
// класс для обработкиличного кабинета или кнопок авторизации в хэдере сайта

@Component
@SessionScope
public class HeaderMenuUtil {
    private AuthorDTO authorDTO;
    private String principalName;
    private AuthorService authorService;

    public HeaderMenuUtil(AuthorService authorService) {
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!principalName.equals("anonymousUser")){;
            System.out.println("not anonymousIser");
            System.out.println(authorService == null);
            this.principalName = principalName;
            this.authorDTO = authorService.getAuthorDTOByEmail(this.principalName);
        } else {
            System.out.println("Session created");
            this.principalName = "default";
        }
    }


    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
        System.out.println(this.principalName);
        System.out.println(authorService == null);
        this.authorDTO = authorService.getAuthorDTOByEmail(this.principalName);
    }

    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    public AuthorDTO getAuthorDTO() {
        return authorDTO;
    }

    public String getPrincipalName() {
        return principalName;
    }
}
