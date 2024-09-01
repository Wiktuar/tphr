package ru.tphr.tphr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tphr.tphr.DTO.LikesAlbumDto;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.music.AlbumService;
import ru.tphr.tphr.utils.HeaderMenuUtil;

import java.security.Principal;
import java.util.Set;

@Controller
public class MusicPageController {
    private AlbumService albumService;
    private AuthorService authorService;
    private HeaderMenuUtil headerMenuUtil;

    @Autowired
    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setHeaderMenuUtil(HeaderMenuUtil headerMenuUtil) {
        this.headerMenuUtil = headerMenuUtil;
    }

    //  метод получения всех альбомов автора
    @GetMapping("/cabinet/music")
    public String getAllAlbums(Principal principal,
                               Model model){
        Set<LikesAlbumDto> albums = albumService.getAlbumsByUser(principal.getName());
        model.addAttribute("albums", albums);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return "cabinet/musics";
    }

//  метод получения отдельного альбома по его ID
    @GetMapping("/cabinet/music/{id}")
    public String getAllAlbums(@PathVariable("id") long id,
                               Principal principal,
                               Model model){
        LikesAlbumDto album = albumService.getLikesAlbumDto(principal.getName(), id);
        album.setFirstName("Виктор");
        album.setLastName("Гусев");
        album.setPathToAvatar("\\wiktuar@yandex.ru\\avatar.jpg");
        model.addAttribute("album", album);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return "cabinet/music";
    }
}
