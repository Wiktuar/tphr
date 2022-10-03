package ru.tphr.tphr.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.entities.security.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
}
