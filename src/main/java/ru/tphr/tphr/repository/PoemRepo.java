package ru.tphr.tphr.repository.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.DTO.EditPoemDTO;
import ru.tphr.tphr.DTO.LikesDto;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.entities.Poem;

import java.util.List;

@Repository
public interface PoemRepo extends CrudRepository<Poem, Long> {

//  это альтернативный вариант. Можно доставать всего автора, а можно просто его ID
//    Set<Poem> getAllByAuthor(Author author);
    @Query("SELECT p FROM Poem p WHERE p.author.id = :id ORDER BY p.releaseDate DESC")
    List<Poem> getAllPoemsByAuthorId(@Param("id") long id);

//  получение PoemDTO для редактирования стизотворения на фронтенде
    @Query("select new ru.tphr.tphr.DTO.EditPoemDTO(p.id, p.header, p.content, p.fileName, p.releaseDate) from Poem p WHERE p.id = :id")
    EditPoemDTO getEditPoemDTO(@Param("id") Long id);

//  получение PoemDTO вместе с количеством лайков и комментариев.
//  определение лайкнул ли пользователь стихотворение или нет.
    @Query("select new ru.tphr.tphr.DTO.LikesPoemDto(p.id, p.header, p.content, p.fileName, p.releaseDate, p.likes.size, p.comments.size, " +
            "sum(case when pl.email = :email then 1 else 0 end) > 0 ) " +
            "from Poem p left join p.likes pl group by p having p.id = :id" )
    LikesPoemDto getPoemWithLikesAndComments(@Param("email") String email, @Param("id") long id);

//  получение списка пользователей, поставивших лайки стихотворению
    @Query("from Poem p left join fetch p.likes where p.id = :id")
    Poem getListOfLikes(@Param("id") long id);
}

