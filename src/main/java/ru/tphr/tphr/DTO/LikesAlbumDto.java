package ru.tphr.tphr.DTO;

import lombok.Getter;
import lombok.Setter;
import ru.tphr.tphr.entities.music.Song;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LikesAlbumDto extends CompositionDTO {
    private String songPreview;
    private Song song;

    public LikesAlbumDto(long id, String header, String fileName, String releaseDate, String songPreview,
                         String email, String firstName, String lastName, String pathToAvatar,
                         int likes, int comments, boolean meLiked) {
        super(id, header, fileName, releaseDate, email, firstName, lastName, pathToAvatar, likes, comments, meLiked);
        this.songPreview = songPreview;
    }

    public LikesAlbumDto(long id, String header, String fileName, String releaseDate, String songPreview,
                         String email, String firstName, String lastName, String pathToAvatar,
                         Song song, int likes, int comments, boolean meLiked) {
        super(id, header, fileName, releaseDate, email, firstName, lastName, pathToAvatar, likes, comments, meLiked);
        this.songPreview = songPreview;
        this.song = song;
    }

    public LikesAlbumDto(long id, String header, String fileName, String releaseDate, int likes, int comments, boolean meLiked) {
        super(id, header, fileName, releaseDate, likes, comments, meLiked);
    }
}
