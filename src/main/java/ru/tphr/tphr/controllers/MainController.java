package ru.tphr.tphr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.tphr.tphr.DTO.AuthorDTO;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.ContentService;
import ru.tphr.tphr.services.PoemService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    private PoemService poemService;
    private AuthorService authorService;
    private ContentService contentService;

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

    //  метод получения стихотворений на индексной странице
    @GetMapping("/")
    public String getMainPage(Model model, Principal principal){
        AuthorDTO authorDTO = null;
        String principalName = null;
        if(principal != null){
            principalName = principal.getName();
            authorDTO = authorService.getAuthorDTOByEmail(principalName);
        } else {
            principalName = "default";
        }
        List<LikesPoemDto> lpd =  poemService.getAllPoems(principalName);
        model.addAttribute("authorDTO", authorDTO);
        model.addAttribute("poems", lpd);
        return "index";
    }


    //  метод, возвращающий стихотворение с его лайками и комментариями
    @GetMapping("/main/poem/{id}")
    public String getPoemById(@PathVariable long id,
                              Principal principal,
                              Model model){
        AuthorDTO authorDTO = null;
        String userName = null;
        if(principal != null){
            userName = principal.getName();
            authorDTO = authorService.getAuthorDTOByEmail(userName);
            model.addAttribute("authorDTO", authorDTO);
        } else {
            userName = "default";
        }

        LikesPoemDto likesPoemDto = poemService.getPoemDtoWithLikesAndComments(userName, id);
        String content = contentService.findById(id).getContent();
        likesPoemDto.setContent(content);
        model.addAttribute("poem", likesPoemDto);
        return "single/singlePoem";
    }

//    @PostMapping("/fail")
//    public RedirectView getLoginPage(@RequestParam String username,
//                                     @RequestParam String password,
//                                     HttpServletRequest request,
//                                     RedirectAttributes ra){
//        System.out.println(username);
//        ra.addFlashAttribute("flashAttr", "Пожалуста, подтвердите, что Вы не робот!");
//        ra.addFlashAttribute("username", username);
//        ra.addFlashAttribute("password", password);;
//        return new RedirectView("/login", true);
//    }

//    @GetMapping("/auth-error")
//    public RedirectView getFailAuth(@RequestParam String username,
//                              @RequestParam String password,
//                              HttpServletRequest request,
//                              RedirectAttributes ra){
//        System.out.println("Вызов GET метода при ошибке " + username);
//        System.out.println("Вызов GET метода при ошибке " + password);
//        ra.addFlashAttribute("username", username);
//        ra.addFlashAttribute("password", password);
//        ra.addFlashAttribute("attention", "true");
//        return new RedirectView("/login", true);
//    }
}
