package ru.tphr.tphr.services.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tphr.tphr.DTO.LikesAlbumDto;
import ru.tphr.tphr.entities.music.Album;
import ru.tphr.tphr.repository.music.AlbumRepo;
import ru.tphr.tphr.services.AuthorService;

import java.util.Set;


@Service
public class AlbumService {
    private AuthorService authorService;
    private AlbumRepo albumRepo;

    @Autowired
    public void setAlbumRepo(AlbumRepo albumRepo) {
        this.albumRepo = albumRepo;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    //  метод, сохраняющий альбом и его песни
    @Transactional
    public Album saveAlbum(Album album){
       return albumRepo.save(album);
    }

//  метод, возвращающий все альбоы одного пользователя с превью-песней, лайками и комментариями
    @Transactional
    public Set<LikesAlbumDto> getAlbumsByUser(String email){
        Set<LikesAlbumDto> albums = albumRepo.getAlbumsByUser(email);
        return albums;
    }

//  метод получения одного LikeAlbumDto по его ID
    public LikesAlbumDto getLikesAlbumDto(String email, long id){
        return albumRepo.getAlbumDto(email, id);
    }

//  метод проверки существует ли музыкальный альбом с таким названием или нет
    public boolean checkAlbumNotExists(String header, String email){
        return albumRepo.getAlbumId(header, email) == null;
    }
}
