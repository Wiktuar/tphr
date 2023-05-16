package ru.tphr.tphr.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

//@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String categoryName;

//    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private Set<Poem> poems ;
}
