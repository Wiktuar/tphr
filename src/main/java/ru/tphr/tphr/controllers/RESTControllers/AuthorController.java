package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.tphr.tphr.DTO.CaptchaResponseDto;
import ru.tphr.tphr.DTO.CompositionDTO;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.exceptions.AuthorExistsException;
import ru.tphr.tphr.services.AllComposeService;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.utils.HeaderMenuUtil;
import ru.tphr.tphr.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
public class AuthorController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${upload.pathW}")
    private String uploadPath;

    @Value("${source.path}")
    private String fromPath;

    @Value("${google.recaptcha.key.secret}")
    private String secret;

    private RestTemplate restTemplate;
    private HeaderMenuUtil headerMenuUtil;
    private AuthorService authorService;
    private AllComposeService allComposeService;

    @Autowired
    public void setHeaderMenuUtil(HeaderMenuUtil headerMenuUtil) {
        this.headerMenuUtil = headerMenuUtil;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setAllComposeService(AllComposeService allComposeService) {
        this.allComposeService = allComposeService;
    }

    //    метод сначала проверяет есть ли пользователь в базе с таким email,
    //    а потом сохраняет
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

        author.setDescription(Utils.addBrTagDescription(author.getDescription()));
        Utils.changeSocialNetsAndDescription(author);

        authorService.saveAuthor(author);
        return ResponseEntity.ok().build();
    }

//  метод сохранения отредактированного автора
    @PostMapping("/editauthor")
    public ResponseEntity saveEditAuthor(@ModelAttribute Author author,
                                     @RequestParam("oldPath") String oldPath,
                                     Principal principal) throws IOException{

        author.setDescription(Utils.addBrTagDescription(author.getDescription()));

        if(!author.getPathToAvatar().equals(oldPath)){
            Path targetPath = Paths.get(uploadPath + "/" + principal.getName() + "/avatars");
            setPathToAvatar(author, targetPath);
        }

        Utils.changeSocialNetsAndDescription(author);
        authorService.saveEditAuthor(author);

        if(!principal.getName().equals(author.getEmail())){
            File dir = new File(uploadPath + "/" + principal.getName());
            boolean bool = dir.renameTo(new File(uploadPath + "/" + author.getEmail()));
            if(bool) authorService.updateAuthorEmail(author);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.ok().build();
    }

    public List<? extends CompositionDTO> getAllAuthorsCompositions(@PathVariable long id){
        return allComposeService.getAllComposeDtoByAuthorId(headerMenuUtil.getPrincipalName(), id);
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
