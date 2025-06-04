package ru.tphr.tphr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tphr.tphr.DTO.AllComposeDTO;
import ru.tphr.tphr.DTO.CompositionDTO;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.services.AllComposeService;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.ContentService;
import ru.tphr.tphr.services.PoemService;
import ru.tphr.tphr.utils.HeaderMenuUtil;
import ru.tphr.tphr.utils.Utils;

import java.util.List;

@Controller
public class MainController {

    private PoemService poemService;
    private AuthorService authorService;
    private ContentService contentService;
    private AllComposeService allComposeService;
    private HeaderMenuUtil headerMenuUtil;

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

    @Autowired
    public void setAllComposeService(AllComposeService allComposeService) {
        this.allComposeService = allComposeService;
    }

    @Autowired
    public void setHeaderMenuUtil(HeaderMenuUtil headerMenuUtil) {
        this.headerMenuUtil = headerMenuUtil;
    }

    //  метод получения стихотворений на индексной странице
    @GetMapping("/")
    public String getMainPage(Model model){
        List<? extends CompositionDTO> acd = allComposeService.getAllCompose(headerMenuUtil.getPrincipalName());
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        model.addAttribute("allComposDTO", acd);
        return "index";
    }


    //  метод, возвращающий стихотворение с его лайками и комментариями
    @GetMapping("/main/poem/{id}")
    public String getPoemById(@PathVariable long id,
                              Model model){
        LikesPoemDto likesPoemDto = poemService.getPoemDtoWithLikesAndComments(headerMenuUtil.getPrincipalName(), id);
        likesPoemDto.setReleaseDate(Utils.getFormatedDate(likesPoemDto.getReleaseDate()));
        String content = contentService.findById(id).getContent();
        likesPoemDto.setContent(content);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        model.addAttribute("poem", likesPoemDto);
        return "single/singlePoem";
    }

//  метод для аутентификации для лайка стихотворения
    @GetMapping("/target/poem/{id}")
    public String getLoginPoem(@PathVariable String id){
        String targetString = "/main/poem/" + id;
        return "redirect:" + targetString;
    }

//  метод для аутентификации для лайка музыкального альбома
    @GetMapping("/target/album/{id}")
    public String getLoginAlbum(@PathVariable String id){
        String targetString = "/main/music/" + id;
        return "redirect:" + targetString;
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
