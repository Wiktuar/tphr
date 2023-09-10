package ru.tphr.tphr.repository.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.DTO.EditPoemDTO;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.entities.poem.Poem;

import java.util.List;

@Repository
public interface PoemRepo extends CrudRepository<Poem, Long> {

//  это альтернативный вариант. Можно доставать всего автора, а можно просто его ID
//    Set<Poem> getAllByAuthor(Author author);
    @Query("SELECT p FROM Poem p WHERE p.author.email = :email ORDER BY p.releaseDate DESC")
    List<Poem> getAllPoemsByAuthorEmail(@Param("email") String email);

//  получение PoemDTO для редактирования стизотворения на фронтенде
    @Query("select new ru.tphr.tphr.DTO.EditPoemDTO(p.id, p.header, p.fileName, p.releaseDate) from Poem p WHERE p.id = :id")
    EditPoemDTO getEditPoemDTO(@Param("id") Long id);

//  получение PoemDTO вместе с количеством лайков и комментариев.
//  определение лайкнул ли пользователь стихотворение или нет.
    @Query("select new ru.tphr.tphr.DTO.LikesPoemDto(p.id, p.header,p.fileName, p.releaseDate, p.likes.size, p.comments.size, " +
            "sum(case when pl.email = :email then 1 else 0 end) > 0 ) " +
            "from Poem p left join p.likes pl group by p having p.id = :id" )
    LikesPoemDto getPoemWithLikesAndComments(@Param("email") String email, @Param("id") long id);

//    @Query("from Poem p join fetch p.likes pl join fetch p.comments pc where    p.author.id = 1" )
//    List<Poem> getListPoemsWithLikesAndComments(@Param("email1") String email1,
//                                                                    @Param("email2") String email2);


    //  получение списка пользователей, поставивших лайки стихотворению
    @Query("from Poem p left join fetch p.likes where p.id = :id")
    Poem getListOfLikes(@Param("id") long id);

//  получение LikesPoemDto для конкретного пользователя
    @Query("select new ru.tphr.tphr.DTO.LikesPoemDto(p.id, p.header,p.fileName, p.releaseDate, p.poemPreview, p.author.email, p.author.firstName, p.author.lastName, p.author.pathToAvatar, p.likes.size, p.comments.size, " +
            "sum(case when pl.email = :email then 1 else 0 end) > 0 ) " +
            "from Poem p left join p.likes pl group by p having p.author.email = :em")
    List<LikesPoemDto> getPoemsByUser(@Param("email") String email, @Param("em") String em);

//  получение всех стихов всех авторов
    @Query("select new ru.tphr.tphr.DTO.LikesPoemDto(p.id, p.header,p.fileName, p.releaseDate, p.poemPreview, p.author.email, p.author.firstName, p.author.lastName, p.author.pathToAvatar, p.likes.size, p.comments.size, " +
            "sum(case when pl.email = :email then 1 else 0 end) > 0 ) " +
            "from Poem p left join p.likes pl group by p")
    List<LikesPoemDto> getAllPoem(@Param("email") String email);
}

