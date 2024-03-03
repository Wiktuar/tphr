package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tphr.tphr.entities.music.Album;
import ru.tphr.tphr.entities.music.Song;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.music.AlbumService;
import ru.tphr.tphr.utils.Utils;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
public class MusicController {
    @Value("${source.path3}")
    private String uploadPath;

    @Value("${albumCover.path}")
    private String albumCoverPath;

    private AuthorService authorService;
    private AlbumService albumService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }

//  метод, сохраняющий альбом и песни
    @PostMapping("/savemusic")
    public String saveMusic(@RequestParam("albumName") String albumName,
                            @RequestParam("cover") String coverImage,
                            @RequestParam("header") String[] headers,
                            @RequestParam("file") MultipartFile[] files,
                            Principal principal) throws IOException {

        Album album = new Album();
        album.setHeader(albumName);
        album.setReleaseDate(Utils.convertTimeToString());
        album.setAuthor(authorService.getAuthorByEmail(principal.getName()));

        Path targetPath = Paths.get(uploadPath + principal.getName() + "\\music\\" + albumName);

        if (Files.exists(targetPath)) {
            targetPath = Paths.get(uploadPath + principal.getName() + "\\music\\" + UUID.randomUUID() + albumName);
        }
        Files.createDirectories(targetPath);

        if(coverImage.equals("defaultCover.png")){
            Files.copy(Paths.get(albumCoverPath), Paths.get(targetPath.toString() + "\\defaultCover.png"));
            album.setFileName(principal.getName() + "/music/" + albumName + "/defaultCover.png");
        } else {
            Utils.saveCircumcisedImage(targetPath.toString(), coverImage, "\\albumCover.jpg");
            album.setFileName(principal.getName() + "/music/" + albumName + "/albumCover.jpg");
        }

        for (int i = 0; i < headers.length; i++) {
           if(headers[i].equals(""))continue;
           Song song = new Song();
           song.setHeader(headers[i]);
           String fileName = targetPath.toString() + "\\" + files[i].getOriginalFilename();
           files[i].transferTo(new File(fileName));
            try {
                String duration = Utils.getMusicFileDuration(fileName);
                song.setDuration(duration);
            } catch (UnsupportedAudioFileException e) {
                System.out.println(e.getMessage());
            }
            song.setUrlToMusicFile(principal.getName() + "/music/" + albumName + "/" + files[i].getOriginalFilename());
           if(i == 0 ){
               album.setSongPreview(principal.getName() + "/music/" + albumName + "/" + files[i].getOriginalFilename());
           }
           album.addSong(song);
        }

        albumService.saveAlbum(album);

        return "ok";
    }

//  метод, возвращающий альбомм со всеми песнямми
    @GetMapping("/cabinet/album/{id}")
    public String getSingleAlbum(@PathVariable long id){
        Album album = albumService.getAlbumWithSongs(id);
        List<Song> songs = album.getSongs();
        songs.forEach(s -> System.out.println(s.getHeader()));
        return "ok";
    }
}
