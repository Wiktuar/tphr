package ru.tphr.tphr.entities;

import lombok.Data;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.entities.Category;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "poems")
public class Poem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String header;
    private String content;
    private LocalDate releaseDate;
    private String fileName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "poems_tags",
            joinColumns = @JoinColumn(name = "poem_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Category> categories;
}
