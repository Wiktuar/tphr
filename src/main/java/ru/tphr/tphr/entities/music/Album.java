package ru.tphr.tphr.entities.music;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tphr.tphr.DTO.AuthorDTO;
import ru.tphr.tphr.entities.Comment;
import ru.tphr.tphr.entities.security.Author;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "header")
    private String header;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "song_preview")
    public String songPreview;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "author_id")
    private Author author;

    @Transient
    private AuthorDTO authorDTO;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(song);
        song.setAlbum(this);
    }

    public void removeSong(Song song) {
        songs.remove(song);
        song.setAlbum(null);
    }

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "poem_id")
//  поле poem.id у комментария должно позволять устанавливать значение в NULL
    private List<Comment> comments;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
//    @JoinTable(
//            name = "poems_likes",
//            joinColumns = @JoinColumn(name = "poem_id"),
//            inverseJoinColumns = @JoinColumn(name = "author_id")
//    )
//    private Set<Author> likes = new HashSet<>();

    //  equals and hashCode
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Poem poem = (Poem) o;
//        return id == poem.id && header.equals(poem.header) && releaseDate.equals(poem.releaseDate) && fileName.equals(poem.fileName) && poemPreview.equals(poem.poemPreview);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, header, releaseDate, fileName, songPreview);
//    }
}
