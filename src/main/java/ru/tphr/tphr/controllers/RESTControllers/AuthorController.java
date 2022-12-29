package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.exceptions.AuthorExistsException;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/check")
public class AuthorController {
    @Value("${upload.path}")
    private String uploadPath;

    @Value("${source.path}")
    private String fromPath;

    private AuthorService authorService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

//    метод сначала проверяет есть ли польователь в базе с таким email,
//    а птом сохраняет
    @PostMapping
    public HttpStatus saveAuthorIfNotExists(@ModelAttribute Author author) throws IOException {
         if(authorService.getAuthorByEmail(author.getEmail()) != null ) throw new AuthorExistsException();
    // создаем папку автора, куда будет сохранен его аватар
        Path targetPath = Paths.get(uploadPath + "\\" + author.getEmail());
        Files.createDirectory(targetPath);

    // если аватар у автора дефолтный, то копируем его из папки проекта
        if(author.getPathToAvatar().equals("defaultAva.png")){
            Path sourcePath = Paths.get(fromPath);
            Files.copy(sourcePath, Paths.get(targetPath + "\\defaultAva.png"));
            author.setPathToAvatar("\\" + author.getEmail() + "/" + author.getPathToAvatar());
        } else { //  Если же пользователь добавил аватар, тогда...
            Utils.saveAuthorsAvatar(targetPath.toString(), author.getPathToAvatar());
            author.setPathToAvatar("\\" + author.getEmail() + "\\avatar.jpg");
        }

      authorService.saveAuthor(author);
        return HttpStatus.OK;
    }
}
