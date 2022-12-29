package ru.tphr.tphr.repository.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.entities.security.PasswordResetToken;

@Repository
public interface PasswordTokenRepo extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
