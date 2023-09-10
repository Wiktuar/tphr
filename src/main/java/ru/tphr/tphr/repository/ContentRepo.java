package ru.tphr.tphr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.entities.poem.Content;

@Repository
public interface ContentRepo extends JpaRepository<Content, Long> {

}
