package ru.tphr.tphr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.entities.AllCompose;
import ru.tphr.tphr.entities.Composition;

import java.util.List;

@Repository
public interface CompositionRepo extends JpaRepository<Composition, Long> {

    //  метод, возвращающий список авторов, поставивших лайк произведению.
    @Query("from Composition c left join fetch c.likes where c.id = :id")
    Composition getListOfLikes(@Param("id") long id);

    @Override
    List<Composition> findAll();

    List<Composition> findAllByAuthorId(long id);
}
