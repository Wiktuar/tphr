package ru.tphr.tphr.repository.music;

import org.springframework.data.repository.CrudRepository;
import ru.tphr.tphr.entities.music.Song;

import java.util.Set;

public interface SongRepo extends CrudRepository<Song, Long> {
    //метод получения песен по ID альбома
    Set<Song> getAllByAlbumId(Long id);
}
