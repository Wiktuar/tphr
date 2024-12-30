package ru.tphr.tphr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.entities.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

}
