package ru.tphr.tphr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.entities.security.PasswordResetToken;
import ru.tphr.tphr.exceptions.TokenExistsException;
import ru.tphr.tphr.services.AuthorService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
public class LoginController {

    @Value("${upload.path}")
    private String pathToAvatar;

    private AuthorService authorService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    // получение страницы регистрации автора
    @GetMapping("registration")
    public String getRegistrationPage(){
        return "personal/registration";
    }

    //  метод получения страницы личного кабинета
    @GetMapping("/cabinet")
    public String getCabinet(Principal principal, Model model){
        Author authorFromDb = authorService.getAuthorByEmail(principal.getName());
        Author author = new Author();
        author.setFirstName(authorFromDb.getFirstName());
        author.setLastName(authorFromDb.getLastName());
        author.setEmail(authorFromDb.getEmail());
        author.setActivationCode(authorFromDb.getActivationCode());
        author.setPathToAvatar(authorFromDb.getPathToAvatar());
        author.setVk("Виктор");
        author.setTg("Виктор");
        author.setYt("Виктор");
        model.addAttribute("author", author);
        return "personal/cabinet";
    }


    // активация аакаунта автора
    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable("code") String code,
                               Model model){
        boolean isActivated = authorService.activateAuthor(code);
        if(isActivated)model.addAttribute("activate", "Поздравляем! Ваш аккаунт успешно активирован.");
        else model.addAttribute("activate", "Активировать аккаунт не получилось. Возможно, он уже активирован." +
                "Попробуйте перейти в личный кабинет или обратитесь в <a href=\"mailto:tech@tphr.ru\" class=\"support\">Техническую поддержку.</a>");
        return "personal/activation";
    }

    @GetMapping("/reset/{token}")
    public String resetPassword(@PathVariable("token") String token,
                                Model model){

        PasswordResetToken prt = null;
        try{
            prt = authorService.getPasswordResetToken(token);
        } catch(TokenExistsException e){
            model.addAttribute("author_nor_found", e.getMessage());
            model.addAttribute("author_id", " ");
            return "personal/resetPassword";
        }
        model.addAttribute("author_id", prt.getAuthor().getId());
        return "personal/resetPassword";
    }


//    метод, помогающий обраотать случай, когда пользователь аутентифицировался, перешел куда, и ему нужно нажать "Назад"
//    и при этом не попасть снова на форму логирования
    @GetMapping("/login")
    public String showLoginForm(Model model,
                                HttpServletRequest request) {
//        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
//        String redirect = null;
//        String userName = null;
//        String password = null;
//        if(map != null){
//            for (Map.Entry<String, ?> entry : map.entrySet()) {
//                String k = entry.getKey();
//                Object v = entry.getValue();
//                System.out.println(k + " " + v);
//            }
//            redirect = (String) map.get("flashAttr");
//            userName = (String) map.get("username");
//            password = (String) map.get("password");
//        }
        System.out.println("Вызов метода");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            System.out.println("Блок аутентификации");
//            if(redirect != null){
//                System.out.println("Записываю атрибуты");
//                model.addAttribute("fail", redirect);
//                model.addAttribute("userName", userName);
//                model.addAttribute("password", password);
//            }
            return "personal/login";
        }
        System.out.println("блок редиректа");
        return "redirect:/";
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