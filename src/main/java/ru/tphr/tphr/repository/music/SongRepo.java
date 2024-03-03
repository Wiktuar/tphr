package ru.tphr.tphr.repository.music;

import org.springframework.data.repository.CrudRepository;
import ru.tphr.tphr.entities.music.Song;

public interface SongRepo extends CrudRepository<Song, Long> {

}
