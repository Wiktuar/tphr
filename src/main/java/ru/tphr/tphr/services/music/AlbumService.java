package ru.tphr.tphr.services.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tphr.tphr.DTO.CompositionDTO;
import ru.tphr.tphr.DTO.EditAlbumDTO;
import ru.tphr.tphr.DTO.LikesAlbumDto;
import ru.tphr.tphr.entities.music.Album;
import ru.tphr.tphr.repository.CompositionRepo;
import ru.tphr.tphr.repository.music.AlbumRepo;
import ru.tphr.tphr.services.AuthorService;

import java.util.List;
import java.util.Set;


@Service
public class AlbumService {
    private CompositionRepo compositionRepo;
    private AlbumRepo albumRepo;

    @Autowired
    public void setAlbumRepo(AlbumRepo albumRepo) {
        this.albumRepo = albumRepo;
    }

    @Autowired
    public void setCompositionRepo(CompositionRepo compositionRepo) {
        this.compositionRepo = compositionRepo;
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

//  Получение CompositionDTO, поскольку его содержание идентично AlbumDTO.
//  Песни будут запрашиваться другим подзапросом через REST сервис.
    public CompositionDTO getLikesAlbumDto(String email, long id){
        return compositionRepo.getCompositionDTO(email, id);
    }

//  метод проверки существует ли музыкальный альбом с таким названием или нет
    public boolean checkAlbumNotExists(String header, String email){
        return albumRepo.getAlbumId(header, email) == null;
    }

//  метод, возвращающий альбом и все его песни для последующего редактирования в виде DTO
    public Album getAlbumWithSongs(long id){
        return albumRepo.getAlbumWithSongs(id);
    }

//  метод получение LikesAlbumDto для конкретного пользователя по его ID
    public List<LikesAlbumDto> getAlbumsByUserID(String email, Long id){
        return albumRepo.getAlumsByAuthorID(email, id);
    }
}
