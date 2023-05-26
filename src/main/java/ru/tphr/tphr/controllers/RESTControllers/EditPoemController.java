package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.tphr.tphr.DTO.PoemDTO;
import ru.tphr.tphr.services.PoemService;
import ru.tphr.tphr.utils.Utils;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class EditPoemController {
    private PoemService poemService;

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @GetMapping("/cabinet/updatete/poem/{id}")
    public PoemDTO getPoemById(@PathVariable long id){
        PoemDTO poemDTO = poemService.getPoemDTO(id);
        poemDTO.setContent(Utils.editPoem(poemDTO.getContent()));
        return poemDTO;
    }
}
