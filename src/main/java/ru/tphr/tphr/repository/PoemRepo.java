package ru.tphr.tphr.repository.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.DTO.PoemDTO;
import ru.tphr.tphr.entities.Poem;

import java.util.List;

@Repository
public interface PoemRepo extends CrudRepository<Poem, Long> {

//  это альтернативный вариант. Можно доставать всего автора, а можно просто его ID
//    Set<Poem> getAllByAuthor(Author author);
    @Query("SELECT p FROM Poem p WHERE p.author.id = :id ORDER BY p.releaseDate DESC")
    List<Poem> getAllPoemsByAuthorId(@Param("id") long id);

//  получение PoemDTO для редактирования стизотворения на фронтенде
    @Query("select new ru.tphr.tphr.DTO.PoemDTO(p.id, p.header, p.content, p.fileName, p.releaseDate) from Poem p WHERE p.id = :id")
    PoemDTO getPoemDTO(@Param("id") Long id);
}
