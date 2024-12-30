package ru.tphr.tphr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tphr.tphr.entities.Review;
import ru.tphr.tphr.repository.ReviewRepo;
import ru.tphr.tphr.repository.security.AuthorRepo;
import ru.tphr.tphr.utils.Utils;

@Service
public class ReviewService {
    private ReviewRepo reviewRepo;
    private AuthorRepo authorRepo;

    @Autowired
    public void setReviewRepo(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Autowired
    public void setAuthorRepo(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

//  метод получения имени автора отзыва
    public String getNameIfAuthorRegistered(String email){
        return authorRepo.getAuthorDTOByEmail(email).getFirstName();
    }

//  метод сохранения отзыва в базе
    public Review save(Review review){
        review.setDateTime(Utils.convertTimeToString());
        return reviewRepo.save(review);
    }
}
