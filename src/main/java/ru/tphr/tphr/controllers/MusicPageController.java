package ru.tphr.tphr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.tphr.tphr.DTO.AuthorDTO;
import ru.tphr.tphr.entities.music.Album;
import ru.tphr.tphr.entities.music.Song;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.music.AlbumService;
import ru.tphr.tphr.utils.ConvertEntityToDTO;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class MusicPageController {
    private AlbumService albumService;
    private AuthorService authorService;

    @Autowired
    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    //метод получения всех альомов автора
    @GetMapping("/cabinet/music")
    public String getAllAlbums(Principal principal,
                               Model model){
        Set<Album> albums = albumService.getAllAlbumsByAuthorId(principal);
        model.addAttribute("albums", albums);
        return "cabinet/music";
    }
}
