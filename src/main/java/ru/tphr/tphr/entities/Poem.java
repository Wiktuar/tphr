package ru.tphr.tphr.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tphr.tphr.entities.security.Author;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "poems")
public class Poem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "header")
    private String header;

    @Column(name = "content")
    private String content;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "poem_preview")
    public String poemPreview;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "author_id")
    private Author author;

    //    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    @JoinTable(name = "poems_tags",
//            joinColumns = @JoinColumn(name = "poem_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id"))
//    private Set<Category> categories;
}
