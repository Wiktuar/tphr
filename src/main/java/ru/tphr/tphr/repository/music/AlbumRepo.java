package ru.tphr.tphr.repository.music;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.entities.music.Album;

import java.util.List;
import java.util.Set;

@Repository
public interface AlbumRepo extends CrudRepository<Album, Long> {

//  метод, возвращающий все альбоы одного пользователя с превью песней
    @Query("from Album a join fetch a.songs s where a.songPreview = s.urlToMusicFile and a.author.id =:id")
    Set<Album> getAllAlbumsByAuthorId(@Param("id") long id);

//  метод, возвращающий альбом и все его песни
    @Query("SELECT a FROM Album a JOIN FETCH a.songs WHERE a.id = :id")
    Album getAlbumWithSongs(@Param("id") long id);
}
