package ru.tphr.tphr.services.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tphr.tphr.entities.music.Song;
import ru.tphr.tphr.repository.music.SongRepo;

import java.util.Set;

@Service
public class SongService {

    private SongRepo songRepo;

    @Autowired
    public void setAlbumRepo(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    public Set<Song> getAllSongsByAlbumId(long id){
        return songRepo.getAllByAlbumId(id);
    }
}
