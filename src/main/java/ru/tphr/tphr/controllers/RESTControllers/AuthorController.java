package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.exceptions.AuthorExistsException;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/check")
public class AuthorController {
    @Value("${upload.path}")
    private String uploadPath;

    private AuthorService authorService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

//    метод сначала проверяет есть ли польователь в базе с таким email,
//    а птом сохраняет
    @PostMapping
    public HttpStatus saveAuthorIfNotExists(@ModelAttribute Author author) throws IOException {
        //        if(authorService.getAuthorByEmail(author.getEmail()) != null ) throw new AuthorExistsException();
        if(author.getPathToAvatar().equals("defaultAva.png")){
            Path folderPath = Paths.get(uploadPath + "\\" + author.getEmail());
//            Files.createDirectory(folderPath);
            Files.createDirectories(Paths.get("C:\\Users\\wiktu\\Documents\\Java\\projects\\tphr\\src\\main\\resources\\static\\img\\wiktuar"));
            Path sourcePath = Paths.get("C:\\Users\\wiktu\\Documents\\Java\\projects\\tphr\\src\\main\\resources\\staсвtic\\img\\defaultAva.png");
            Path targetPath = Paths.get("C:\\Users\\wiktu\\Documents\\Java\\projects\\tphr\\src\\main\\resources\\static\\img\\wiktuar\\defaultAva1 .png");
            Files.copy(sourcePath, targetPath);
            System.out.println("Копирование завершено");
//            author.setPathToAvatar("\\" + author.getEmail() + "\\" + author.getPathToAvatar());
        }

//
//        Path folderPath = Paths.get(uploadPath + "\\" + author.getEmail());
//        Files.createDirectory(folderPath);
//        Utils.saveAuthorsAvatar(folderPath.toString(), author.getPathToAvatar());
//        author.setPathToAvatar("\\" + author.getEmail() + "\\avatar.jpg");
//
//        authorService.saveAuthor(author);
        return HttpStatus.OK;
    }
}
