package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.tphr.tphr.DTO.CaptchaResponseDto;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.exceptions.AuthorExistsException;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.PoemService;
import ru.tphr.tphr.utils.Utils;

import javax.security.sasl.AuthenticationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Collections;

@RestController
public class AuthorController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${upload.pathW}")
    private String uploadPath;

    @Value("${source.path}")
    private String fromPath;

    @Value("${google.recaptcha.key.secret}")
    private String secret;

    private PoemService poemService;
    private RestTemplate restTemplate;
    private AuthorService authorService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //    метод сначала проверяет есть ли польователь в базе с таким email,
    //    а птом сохраняет
    @PostMapping("/saveauthor")
    public ResponseEntity saveAuthorIfNotExists(@ModelAttribute Author author,
                                                @RequestParam("recaptcha-response") String captchaResponse) throws IOException {
    //  часть кода отвечающаяющая за работу капчи
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if(!response.isSuccess()) return new ResponseEntity(HttpStatus.UNAUTHORIZED);

      if(authorService.getAuthorByEmail(author.getEmail()) != null ) throw new AuthorExistsException();
      // создаем папку автора, куда будет сохранен его аватар
        Path targetPath = Paths.get(uploadPath + "/" + author.getEmail() + "/avatars");
        Files.createDirectories(targetPath);

        setPathToAvatar(author, targetPath);

        Utils.changeSocialNets(author);

        authorService.saveAuthor(author);;
        return ResponseEntity.ok().build();
    }

//  метод сохранения отредактированного автора
    @PostMapping("/editauthor")
    public ResponseEntity saveEditAuthor(@ModelAttribute Author author,
                                     @RequestParam("oldPath") String oldPath,
                                     Principal principal) throws IOException{

        String[] massOfLines = author.getDescription().split("\\n");
        author.setDescription(Utils.addBrTag(massOfLines));

        if(!author.getPathToAvatar().equals(oldPath)){
            Path targetPath = Paths.get(uploadPath + "/" + principal.getName() + "/avatars");
            setPathToAvatar(author, targetPath);
        }

        Utils.changeSocialNets(author);
        authorService.saveEditAuthor(author);

        if(!principal.getName().equals(author.getEmail())){
            File dir = new File(uploadPath + "/" + principal.getName());
            boolean bool = dir.renameTo(new File(uploadPath + "/" + author.getEmail()));
            if(bool) authorService.updateAuthorEmail(author);
            System.out.println(1);
            return ResponseEntity.accepted().build();
        }
        System.out.println(2);
        return ResponseEntity.ok().build();
    }

    private void setPathToAvatar(Author author, Path targetPath) throws IOException {
        // если аватар у автора дефолтный, то копируем его из папки проекта
        if(author.getPathToAvatar().equals("defaultAva.png")){
            Path sourcePath = Paths.get(fromPath);
            if(Files.notExists(Paths.get(targetPath + "/defaultAva.png")))
                Files.copy(sourcePath, Paths.get(targetPath + "/defaultAva.png"));
            author.setPathToAvatar(author.getEmail() + "/avatars/" + author.getPathToAvatar());
        } else { //  Если же пользователь добавил аватар, тогда...
            Utils.saveCircumcisedImage(targetPath.toString(), author.getPathToAvatar(), "\\avatar.jpg");
            author.setPathToAvatar(author.getEmail() + "/avatars/avatar.jpg");
        }
    }
}
