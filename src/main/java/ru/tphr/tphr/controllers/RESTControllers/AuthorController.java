package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.tphr.tphr.DTO.CaptchaResponseDto;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.exceptions.AuthorExistsException;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.PoemService;
import ru.tphr.tphr.utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@RestController
@RequestMapping("/check")
public class AuthorController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${upload.path}")
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
    @PostMapping
    public HttpStatus saveAuthorIfNotExists(@ModelAttribute Author author,
                                            @RequestParam("recaptcha-response") String captchaResponse) throws IOException {
    //  часть кода отвечающаяющая за работу капчи
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if(!response.isSuccess()) return HttpStatus.CONFLICT;

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
