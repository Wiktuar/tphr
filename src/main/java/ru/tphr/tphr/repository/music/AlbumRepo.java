package ru.tphr.tphr.repository.music;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.DTO.LikesAlbumDto;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.entities.music.Album;

import java.util.List;
import java.util.Set;

@Repository
public interface AlbumRepo extends CrudRepository<Album, Long> {

//  метод, возвращающий все альбоы одного пользователя с превью песней
//    @Query("from Album a join fetch a.songs s where a.songPreview = s.urlToMusicFile and a.author.id =:id")
//    Set<Album> getAllAlbumsByAuthorId(@Param("id") long id);

//  метод, возвращающий альбом и все его песни
//    @Query("SELECT a FROM Album a JOIN FETCH a.songs WHERE a.id = :id")
//    Album getAlbumWithSongs(@Param("id") long id);

    //  получение всех LikesAlbumDto для конкретного пользователя
    @Query("select new ru.tphr.tphr.DTO.LikesAlbumDto(a.id, a.header,a.fileName, a.releaseDate, a.songPreview, a.author.email, a.author.firstName, a.author.lastName, a.author.pathToAvatar, (select s from Song s where s.urlToMusicFile = a.songPreview), size(a.likes) , size(a.comments) , " +
            "sum(case when al.email = :email then 1 else 0 end) > 0 ) " +
            "from Album a left join a.likes al group by a having a.author.email = :email")
    Set<LikesAlbumDto> getAlbumsByUser(@Param("email") String email);

//  получение LikesAlbumDTO по его ID вместе с количеством лайков и комментариев.
//  определение лайкнул ли пользователь стихотворение или нет.
    @Query("select new ru.tphr.tphr.DTO.LikesAlbumDto(a.id, a.header, a.fileName, a.releaseDate, size(a.likes) , size(a.comments) , " +
            "sum(case when al.email = :email then 1 else 0 end) > 0 ) " +
            "from Album a left join a.likes al group by a having a.id = :id" )
    LikesAlbumDto getAlbumDto(@Param("email") String email, @Param("id") long id);
}
