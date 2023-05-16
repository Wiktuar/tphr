package ru.tphr.tphr.repository.security;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.DTO.AuthorDTO;
import ru.tphr.tphr.entities.security.Author;

@Repository
public interface AuthorRepo extends CrudRepository<Author, Long> {
    Author findByEmail(String email);
    Author findByActivationCode(String code);

    @Query("SELECT a.id from Author a WHERE a.email = :email")
    long getAuthorId(@Param("email") String email);

//  если значение поля обновляется, то обязптельна аннотация @Modifying
    @Modifying
    @Query("UPDATE Author a SET a.password = :pass WHERE a.id = :id")
    void updateAuthor(@Param("pass") String password, @Param("id") long id);

}
