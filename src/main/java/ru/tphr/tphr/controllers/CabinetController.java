package ru.tphr.tphr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tphr.tphr.entities.Poem;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.PoemService;
import ru.tphr.tphr.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class CabinetController {
    @Value("${upload.path2}")
    private String uploadPath;

    @Value("${source.path2}")
    private String sourcePath2;

    private PoemService poemService;
    private AuthorService authorService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
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

//  метод получения одного стиъотворения по его ID
    @GetMapping("/cabinet/poem/{id}")
    public String getPoemById(@PathVariable long id,
                              Model model){
        Poem poem = poemService.getPoemById(id);
        model.addAttribute("poem", poem);
        return "cabinet/poem";
    }

//  метод, добавляющий стихотворение в базу данных.
    @PostMapping("/cabinet/poems")
    public String savePoemInBD(@ModelAttribute Poem poem,
                               @RequestParam("file") MultipartFile file,
                               Principal principal) throws IOException {
//      если отсутствует имя в Multipart file, значит пользователь решил использовать
//      для обложки картинку по умолчанию
        File uploadFolder = new File(uploadPath + "\\" + principal.getName());
        if(!uploadFolder.exists()){
            uploadFolder.mkdirs();
        }

        if(file.getOriginalFilename().isEmpty()){
            String fileName = UUID.randomUUID().toString();
            File sourceFile = new File(sourcePath2);
            File destFile = new File(uploadPath + "\\"  + principal.getName() + "\\" + fileName + "_" + "poemCover.jpg");
            Files.copy(sourceFile.toPath(), destFile.toPath());
            poem.setFileName(principal.getName() + "\\" + fileName + "_" + "poemCover.jpg");
        } else {
            String fileName = UUID.randomUUID().toString();
            String resultFileName = uploadPath + "\\"  + principal.getName() + "\\" + fileName + "_" + file.getOriginalFilename();
            try(FileOutputStream fos = new FileOutputStream(resultFileName)){
                fos.write(file.getBytes());
            }
            poem.setFileName(principal.getName() + "\\" + fileName + "_" + file.getOriginalFilename());
        }

        String[] massOfLines = poem.getContent().split("\\n");
        poem.setContent(Utils.getAllPoem(massOfLines));
        poem.setPoemPreview(Utils.getPoemPreview(massOfLines));
        poem.setReleaseDate(Utils.conbertTimetoStrimg());
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
