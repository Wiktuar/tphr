package ru.tphr.tphr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.services.*;
import ru.tphr.tphr.utils.HeaderMenuUtil;

import java.security.Principal;
import java.util.List;

@Controller
public class CabinetController {
    @Value("${upload.path}")
    private String uploadPath;

    @Value("${source.path2}")
    private String sourcePath2;

    @Value("${source.path3}")
    private String deletePath;

    private PoemService poemService;
    private AuthorService authorService;
    private ContentService contentService;
    private HeaderMenuUtil headerMenuUtil;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @Autowired
    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

    @Autowired
    public void setHeaderMenuUtil(HeaderMenuUtil headerMenuUtil) {
        this.headerMenuUtil = headerMenuUtil;
    }

    //  получение всех стихотворений одного автора
    @GetMapping("/cabinet/poems")
    public String getAllLikesPoemDto(Model model,
                                     Principal principal){
        List<LikesPoemDto> lpd =  poemService.getPoemsByUser(principal.getName(),principal.getName());
        model.addAttribute("poems", lpd);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return "cabinet/poems";
    }


//  метод, возращающий стихотворение с его лайками и комментариями
    @GetMapping("/cabinet/poem/{id}")
    public String getPoemById(@PathVariable long id,
                               Principal principal,
                               Model model){
        LikesPoemDto likesPoemDto = poemService.getPoemDtoWithLikesAndComments(principal.getName(), id);
        String content = contentService.findById(id).getContent();
        likesPoemDto.setContent(content);
        model.addAttribute("poem", likesPoemDto);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return "cabinet/poem";
    }

/*
    блок удаления всех стихотворений не имеет кнопки в личном кабинете.
    Надо поправить.
 */


//  метод удаляющий все стихотворения из базы данных
    @GetMapping("/cabinet/poems/deleteall")
    public String deleteAllPoems(){
        contentService.deleteAllPoems();
        return "redirect:/cabinet/poems";
    }

//  метод, удаляющий стихотворение их базы данных
    @GetMapping("/cabinet/delete/poem/{id}")
    public String deletePoemById(@PathVariable long id){
        contentService.deletePoemById(id);
        return "redirect:/cabinet/poems";
    }

//  метод удаления автора по его ID
    @GetMapping("/author/delete")
    public String deleteAuthorById(){
        authorService.deleteAuthorById(7L);
        return "redirect:/logout";
    }
}
