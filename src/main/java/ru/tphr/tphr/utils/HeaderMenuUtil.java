package ru.tphr.tphr.utils;

import lombok.Getter;
import lombok.Setter;
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

@Setter
@Getter
@Component
@SessionScope
public class HeaderMenuUtil {
    private AuthorDTO authorDTO;
    private String principalName;
    private AuthorService authorService;

    public HeaderMenuUtil(AuthorService authorService) {
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!principalName.equals("anonymousUser")){;
            this.principalName = principalName;
            this.authorDTO = authorService.getAuthorDTOByEmail(this.principalName);
        } else {
            this.principalName = "default";
        }
    }


    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
        this.authorDTO = authorService.getAuthorDTOByEmail(this.principalName);
    }
}
