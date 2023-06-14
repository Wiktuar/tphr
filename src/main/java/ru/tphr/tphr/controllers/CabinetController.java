package ru.tphr.tphr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.entities.Poem;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.CommentService;
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

    //  получение всех стихотворений по id автора
    @GetMapping("/cabinet/getAll")
    public String getPoemsPage(Principal principal,
                               Model model){
        Author author = authorService.getAuthorByEmail(principal.getName());
        List<Poem> poems = poemService.getAllPoemsByAuthorId(author.getId());
        model.addAttribute("author", author);
        model.addAttribute("poems", poems);
        return "cabinet/poems";
    }

//  метод получения одного стихотворения по его ID
//    @GetMapping("/cabinet/poem/{id}")
//    public String getPoemById(@PathVariable long id,
//                              Model model){
//        Poem poem = poemService.getPoemById(id);
//        model.addAttribute("poem", poem);
//        return "cabinet/poem";
//    }

    @GetMapping("/cabinet/poem/{id}")
    public String getPoemById(@PathVariable long id,
                               Principal principal,
                               Model model){
        LikesPoemDto likesPoemDto = poemService.getPoemDtoWithLikesAndComments(principal.getName(), id);
        model.addAttribute("poem", likesPoemDto);
        return "cabinet/poem";
    }

//  метод, добавляющий стихотворение в базу данных.
    @PostMapping("/cabinet/poems")
    public String savePoemInBD(@ModelAttribute Poem poem,
                               @RequestParam("file") MultipartFile file,
                               @RequestParam("oldFileName") String oldFileName,
                               Principal principal) throws IOException {
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
            System.out.println("Сохранение новой картинки");
            if(!oldFileName.isEmpty()){
                System.out.println("Удаление старой картинки");
                Files.delete(Paths.get(deletePath + "\\" + oldFileName));
            }
            String fileName = UUID.randomUUID().toString();
            String resultFileName = uploadFolder + "\\" + fileName + "_" + file.getOriginalFilename();
            try(FileOutputStream fos = new FileOutputStream(resultFileName)){
                fos.write(file.getBytes());
            }
            poem.setFileName(principal.getName() + "\\" + fileName + "_" + file.getOriginalFilename());
        } else {
            System.out.println("Сохранеи при обновлении предыдущего фото");
            poem.setFileName(oldFileName);
        }

        String[] massOfLines = poem.getContent().split("\\n");
        poem.setContent(Utils.editPoem(massOfLines));
        poem.setPoemPreview(Utils.getPoemPreview(massOfLines));
        if(poem.getReleaseDate().isEmpty()){
            poem.setReleaseDate(Utils.convertTimeToString());
        }
        Author author = authorService.getAuthorByEmail(principal.getName());
        poem.setAuthor(author);
        poemService.savePoemInDB(poem);
        return "redirect:/cabinet/getAll";
    }

    @GetMapping("/cabinet/delete/poem/{id}")
    public String deletePoemById(@PathVariable long id){
        try{
            poemService.deletePoem(id);
        }catch(IOException e){
            System.out.println("Удаление стихотворения не удалось");
        }
        return "redirect:/cabinet/getAll";
    }
}
