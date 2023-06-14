package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.tphr.tphr.DTO.LikesDto;
import ru.tphr.tphr.entities.Poem;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.PoemService;

import java.security.Principal;

@RestController
public class LikesController {
    private AuthorService authorService;
    private PoemService poemService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @GetMapping("/poem/likes/{id}")
    public String manageLikes(@PathVariable long id,
                              Principal principal){
        Author author = authorService.getAuthorByEmail(principal.getName());
        LikesDto l  = poemService.getLikes(id);
        l.getSetOfLikes().forEach(l1 -> System.out.println(l1.getFirstName()));
//        System.out.println(p == null);
//        System.out.println(p.getLikes().size());
//        p.getLikes().forEach(a -> System.out.println(a.getFirstName()));
//        System.out.println(p.getLikes().size());
//        System.out.println(p.getLikes().contains(author));
//        p.getLikes().remove(author);
//        System.out.println(p.getLikes().size());
//        poemService.savePoemInDB(p);
        return "{\"status\": \"1\"}";
    }
}
