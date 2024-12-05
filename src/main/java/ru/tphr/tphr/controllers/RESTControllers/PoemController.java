package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tphr.tphr.DTO.EditPoemDTO;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.entities.poem.Content;
import ru.tphr.tphr.entities.poem.Poem;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.exceptions.ComposeExistsException;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.ContentService;
import ru.tphr.tphr.services.PoemService;
import ru.tphr.tphr.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@RestController
public class PoemController {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${source.path2}")
    private String defaultCoverPath;

    @Value("${source.path3}")
    private String deletePath;

    private AuthorService authorService;
    private PoemService poemService;
    private ContentService contentService;

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @Autowired
    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }


    //  метод, добавляющий стихотворение в базу данных.
    @PostMapping("/cabinet/poems")
    public HttpStatus savePoem(@ModelAttribute Poem poem,
                               @RequestParam("content") String poemContent,
                               @RequestParam("cover") String coverImage,
                               @RequestParam("oldFileName") String oldFileName,
                               Principal principal) throws IOException {

        if(poem.getId() == 0){
            if(!poemService.checkPoemNotExists(poem.getHeader(), principal.getName())) throw new ComposeExistsException();
        }

        Content content = new Content();

        File uploadFolder = new File(uploadPath + "\\" + principal.getName() + "\\poems\\");
        if(!uploadFolder.exists()){
            uploadFolder.mkdirs();
        }

        if(coverImage.equals("poemCover.jpg")){
            if(! new File(uploadFolder.toString() + "\\poemCover.jpg").exists()){
                Files.copy(Paths.get(defaultCoverPath), Paths.get(uploadFolder.toString() + "\\poemCover.jpg"));
            }
            poem.setFileName(principal.getName() + "/poems/" +  "poemCover.jpg");
        } else {
            if(!oldFileName.isEmpty() && !oldFileName.contains("poemCover.jpg")){
                Files.delete(Paths.get(deletePath + oldFileName));
            }
            Utils.saveCircumcisedImage(uploadFolder.toString(), coverImage,  "\\" + poem.getHeader() + ".jpg");
            poem.setFileName(principal.getName() + "/poems/" + poem.getHeader() + ".jpg");
        }

        String[] massOfLines = poemContent.split("\\n");
        content.setContent(Utils.addBrTag(massOfLines));
        poem.setPoemPreview(Utils.getPoemPreview(massOfLines));
        if(poem.getReleaseDate().isEmpty()){
            poem.setReleaseDate(Utils.convertTimeToString());
        }
        Author author = authorService.getAuthorByEmail(principal.getName());
        poem.setAuthor(author);
        content.setAuthor(author);

//   данная проверка делается, чтобы избежать ошибки
//   detached entity passed to persist, связанной с проблемой предсуществования ID
//   e сущности Poem при ее обновлении
        if(poem.getId() != 0){
            content.setId(poem.getId());
            contentService.savePoemInDB(content);
            poemService.savePoem(poem);
        } else {
////   если же ID == 0, значит мы сохраняем новую сущность
            content.setPoem(poem);
            contentService.savePoemInDB(content);
        }

        return HttpStatus.OK;
    }


    @GetMapping("/cabinet/updatete/poem/{id}")
    public EditPoemDTO getPoemById(@PathVariable long id){
        EditPoemDTO editPoemDTO = poemService.getEditPoemDTO(id);
        String content = contentService.findById(id).getContent();
        editPoemDTO.setContent(Utils.removeBrTag(content));
        return editPoemDTO;
    }

//  метод, возвращающий превью стихотворений для конкретного автора
    @GetMapping("/authors/{id}/poems")
    public ResponseEntity<List<LikesPoemDto>> getPoemsByAuthorId(
            @PathVariable long id,
            Principal principal){
        List<LikesPoemDto> lpd =  poemService.getPoemsByAuthorID(principal.getName(), id);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(lpd.size()))
                .body(lpd);
    }
}
