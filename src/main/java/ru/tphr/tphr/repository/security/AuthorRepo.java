package ru.tphr.tphr.repository.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.entities.security.Author;

@Repository
public interface AuthorRepo extends CrudRepository<Author, Long> {
    Author findByEmail(String email);
    Author findByActivationCode(String code);
}
