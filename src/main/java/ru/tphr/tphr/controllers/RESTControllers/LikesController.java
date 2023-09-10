package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.tphr.tphr.DTO.LikesDto;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.entities.poem.Poem;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.PoemService;

import java.security.Principal;
import java.util.List;

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

//  добавление или удаление лайка
    @GetMapping("/poem/likes/{id}")
    public String manageLikes(@PathVariable long id,
                              Principal principal) {
        Author author = authorService.getAuthorByEmail(principal.getName());
        Poem poem = poemService.getListOfLikes(id);
        boolean status = false;
        if (!poem.getLikes().contains(author)) {
            poem.getLikes().add(author);
            status = true;
            poemService.savePoem(poem);
        } else {
            poem.getLikes().remove(author);
            poemService.savePoem(poem);
        }
        return String.format("{\"status\": %s, \"count\": %d}", status, poem.getLikes().size());
    }
}
