package ru.tphr.tphr.entities.poem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tphr.tphr.entities.Comment;
import ru.tphr.tphr.entities.security.Author;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "poems")
public class Poem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "header")
    private String header;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "poem_preview")
    public String poemPreview;

    @ManyToOne(fetch = FetchType.EAGER,
               cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
//            orphanRemoval = true
    )
    @JoinColumn(name = "poem_id")
//  поле poem.id у комментария должно позволять устанавливать значение в NULL
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "poems_likes",
            joinColumns = @JoinColumn(name = "poem_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> likes = new HashSet<>();

//  equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poem poem = (Poem) o;
        return id == poem.id && header.equals(poem.header) && releaseDate.equals(poem.releaseDate) && fileName.equals(poem.fileName) && poemPreview.equals(poem.poemPreview);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, header, releaseDate, fileName, poemPreview);
    }
}
