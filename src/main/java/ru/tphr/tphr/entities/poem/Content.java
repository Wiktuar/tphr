//https://vladmihalcea.com/hibernate-lazytoone-annotation/
package ru.tphr.tphr.entities.poem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tphr.tphr.entities.security.Author;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contents")
public class Content {
    @Id
    private long id;

    @Column(name = "content")
    private String content;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Poem poem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
}
