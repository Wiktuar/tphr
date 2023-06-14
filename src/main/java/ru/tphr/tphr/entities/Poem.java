package ru.tphr.tphr.entities;

import lombok.*;
import ru.tphr.tphr.entities.security.Author;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode
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

    @ManyToOne(fetch = FetchType.LAZY,
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



    //  для стороны Many принято переопределять equals() и hashCode()
//  они переопределены в анноации lambok
}
