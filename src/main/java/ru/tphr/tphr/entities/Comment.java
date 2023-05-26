package ru.tphr.tphr.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tphr.tphr.DTO.AuthorDTO;
import ru.tphr.tphr.entities.security.Author;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "time_stamp")
    private String timeStamp;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "poem_id")
    private long poemId;
}
