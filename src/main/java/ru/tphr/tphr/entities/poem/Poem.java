package ru.tphr.tphr.entities.poem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tphr.tphr.entities.Composition;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("1")
public class Poem extends Composition {

    @Column(name = "text_preview")
    public String poemPreview;

}
