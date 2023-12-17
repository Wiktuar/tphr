package ru.tphr.tphr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.entities.poem.Content;
import ru.tphr.tphr.entities.poem.Poem;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.CommentService;
import ru.tphr.tphr.services.ContentService;
import ru.tphr.tphr.services.PoemService;
import ru.tphr.tphr.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class CabinetController {
    @Value("${upload.path2}")
    private String uploadPath;

    @Value("${source.path2}")
    private String sourcePath2;

    @Value("${source.path3}")
    private String deletePath;

    private PoemService poemService;
    private AuthorService authorService;
    private CommentService commentService;
    private ContentService contentService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

//  получение всех стихотворений одного автора
    @GetMapping("/cabinet/poems")
    public String getAllLikesPoemDto(Model model,
                                     Principal principal){
        List<LikesPoemDto> lpd =  poemService.getPoemsByUser(principal.getName(),principal.getName());
        model.addAttribute("poems", lpd);
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
        return "cabinet/poem";
    }

//  метод, добавляющий стихотворение в базу данных.
    @PostMapping("/cabinet/poems")
    public String savePoemInBD(@ModelAttribute Poem poem,
                               @RequestParam("content") String poemContent,
                               @RequestParam("file") MultipartFile file,
                               @RequestParam("oldFileName") String oldFileName,
                               Principal principal) throws IOException {

        Content content = new Content();

        File uploadFolder = new File(uploadPath + "\\" + principal.getName());
        if(!uploadFolder.exists()){
            uploadFolder.mkdirs();
        }
//      если отсутствует имя в Multipart file, значит пользователь решил использовать
//      для обложки картинку по умолчанию
        if(file.getOriginalFilename().isEmpty() && oldFileName.isEmpty()){
            Path destPath = Paths.get(uploadFolder + "\\" + "poemCover.jpg");
            if(!Files.exists(destPath)){
                Files.copy(Paths.get(sourcePath2), destPath);
            } else {
                System.out.println("картинка существует");
            }
            poem.setFileName(principal.getName() + "\\" + "poemCover.jpg");
        } else if (!file.getOriginalFilename().isEmpty()){
            if(!oldFileName.isEmpty()){
                Files.delete(Paths.get(deletePath + "\\" + oldFileName));
            }
            String fileName = UUID.randomUUID().toString();
            String resultFileName = uploadFolder + "\\" + fileName + "_" + file.getOriginalFilename();
            try(FileOutputStream fos = new FileOutputStream(resultFileName)){
                fos.write(file.getBytes());
            }
            poem.setFileName(principal.getName() + "\\" + fileName + "_" + file.getOriginalFilename());
        } else {
            poem.setFileName(oldFileName);
        }

        String[] massOfLines = poemContent.split("\\n");
        content.setContent(Utils.editPoem(massOfLines));
        poem.setPoemPreview(Utils.getPoemPreview(massOfLines));
        if(poem.getReleaseDate().isEmpty()){
            poem.setReleaseDate(Utils.convertTimeToString());
        }
        Author author = authorService.getAuthorByEmail(principal.getName());
        poem.setAuthor(author);

//   данная проверка делается, чтобы избежать ошибки
//   detached entity passed to persist, связанной с проблемой предсуществования ID
//   e сущности Poem при ее обновлении
        if(poem.getId() != 0){
            content.setId(poem.getId());
            contentService.savePoemInDB(content);
            poemService.savePoem(poem);
        } else {
//   если же ID == 0, значит мы сохраняем новую сущность
            content.setPoem(poem);
            contentService.savePoemInDB(content);
        }
        return "redirect:/cabinet/poems";
    }

    @GetMapping("/cabinet/delete/poem/{id}")
    public String deletePoemById(@PathVariable long id){
        contentService.deletePoemById(id);

        return "redirect:/cabinet/poems";
    }
}
