package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tphr.tphr.DTO.AllComposeDTO;
import ru.tphr.tphr.entities.music.Album;
import ru.tphr.tphr.entities.music.Song;
import ru.tphr.tphr.exceptions.ComposeExistsException;
import ru.tphr.tphr.services.AllComposeService;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.ComposeService;
import ru.tphr.tphr.services.music.AlbumService;
import ru.tphr.tphr.services.music.SongService;
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

@RestController
public class MusicController {
    @Value("${source.path3}")
    private String uploadPath;

    @Value("${albumCover.path}")
    private String albumCoverPath;

    private AuthorService authorService;
    private AlbumService albumService;
    private SongService songService;
    private ComposeService composeService;
    private AllComposeService allComposeService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Autowired
    public void setSongService(SongService songService) {
        this.songService = songService;
    }

    @Autowired
    public void setComposeService(ComposeService composeService) {
        this.composeService = composeService;
    }

    @Autowired
    public void setAllComposeService(AllComposeService allComposeService) {
        this.allComposeService = allComposeService;
    }

    //  метод, сохраняющий альбом и песни
    @PostMapping("/savemusic")
    public HttpStatus saveMusic(@RequestParam("albumHeader") String albumHeader,
                                @RequestParam("cover") String coverImage,
                                @RequestParam("header") String[] headers,
                                @RequestParam("file") MultipartFile[] files,
                                Principal principal) throws IOException {

        if(!albumService.checkAlbumNotExists(albumHeader, principal.getName())) throw new ComposeExistsException();

        Album album = new Album();
        album.setHeader(albumHeader);
        album.setReleaseDate(Utils.convertTimeToString());
        album.setAuthor(authorService.getAuthorByEmail(principal.getName()));

        Path targetPath = Paths.get(uploadPath + principal.getName() + "\\music\\" + albumHeader);

        Files.createDirectories(targetPath);

        if(coverImage.equals("defaultCover.png")){
            Files.copy(Paths.get(albumCoverPath), Paths.get(targetPath.toString() + "\\defaultCover.png"));
            album.setFileName(principal.getName() + "/music/" + albumHeader + "/defaultCover.png");
        } else {
            Utils.saveCircumcisedImage(targetPath.toString(), coverImage, "\\albumCover.jpg");
            album.setFileName(principal.getName() + "/music/" + albumHeader + "/albumCover.jpg");
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
            song.setUrlToMusicFile(principal.getName() + "/music/" + albumHeader + "/" + files[i].getOriginalFilename());
           if(i == 0 ){
               album.setSongPreview(principal.getName() + "/music/" + albumHeader + "/" + files[i].getOriginalFilename());
           }
           album.addSong(song);
        }

        Album savedAlbum = albumService.saveAlbum(album);

        if (savedAlbum != null)  return HttpStatus.OK;
            else return HttpStatus.BAD_REQUEST;
    }

//  метод, возвращающий альбомм со всеми песнямми
    @GetMapping("/cabinet/songs/{id}")
    public Set<Song> getSongsByAlbumId(@PathVariable long id){
        Set<Song> songs = songService.getAllSongsByAlbumId(id);
        songs.forEach(s -> System.out.println(s.getHeader()));
        return songs;
    }

    @GetMapping("/test")
    public List<AllComposeDTO> test(Principal principal){

        List<AllComposeDTO> ac = allComposeService.getAllCompose(principal.getName());
//        ac.forEach(a -> System.out.println(a.getTextPreview()));
//        List<Composition> comp = composeService.findAll();
//        comp.forEach(c -> {
//            System.out.println(c.getClass());
//        });
        return ac;
    }
}
