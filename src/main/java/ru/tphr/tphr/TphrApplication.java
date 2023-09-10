package ru.tphr.tphr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.tphr.tphr.entities.poem.Content;
import ru.tphr.tphr.entities.poem.Poem;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.ContentService;
import ru.tphr.tphr.services.PoemService;

@SpringBootApplication
//@EnableCaching
public class TphrApplication {
    public static void main(String[] args) {
        SpringApplication.run(TphrApplication.class, args);
    }

//    @Autowired
//    private PoemService poemService;
//
//    @Autowired
//    private AuthorService authorService;
//
//    @Autowired
//    private ContentService contentService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Man man = new Man();
//        man.setName("Vasya");
////        manRepo.save(man);
//        Content content = new Content();
//        content.setContent("This is content");
//        content.setMan(man);
//        contentService.saveContent(content);
//        Content content1 = contentService.findById(5L);

//        Content content = new Content();
//        content.setContent("Увидел Вас, и всё былое \n" +
//                "передо мной явилась ты \n" +
//                "как мимолетное виденье \n" +
//                "как гений чистой красоты!" +
//                "И мир вокруг вжруг озарился" +
//                "Он просиял, словно бриаллиант");
//
//        Poem poem = new Poem();
//        poem.setHeader("Я помню чудное мгновенье");
//        poem.setReleaseDate("2023-06-23 18:53");
//        poem.setFileName("poem.txt");
//        poem.setPoemPreview("Я помню чудное гновенье \n" +
//                "передо мной явилась ты \n" +
//                "как мимолетное виденье \n" +
//                "как гений чистой красоты!");
//
//        poem.setAuthor(authorService.getAuthorByEmail("wiktuar@yandex.ru"));
//        content.setPoem(poem);
//        contentService.saveContent(content);
//
//        System.out.println("Готово!");
//        Poem poem = poemService.getPoemById(20L);
//        System.out.println(poem.getContent().getContent());
//    }
}
