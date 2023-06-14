package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.tphr.tphr.DTO.EditPoemDTO;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.services.PoemService;
import ru.tphr.tphr.utils.Utils;

import java.security.Principal;

@RestController
public class EditPoemController {
    private PoemService poemService;

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @GetMapping("/cabinet/updatete/poem/{id}")
    public EditPoemDTO getPoemById(@PathVariable long id){
        EditPoemDTO editPoemDTO = poemService.getEditPoemDTO(id);
        editPoemDTO.setContent(Utils.editPoem(editPoemDTO.getContent()));
        return editPoemDTO;
    }
}
