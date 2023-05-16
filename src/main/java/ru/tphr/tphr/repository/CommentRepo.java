package ru.tphr.tphr.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.entities.Comment;

import java.util.List;

@Repository
public interface CommentRepo extends CrudRepository<Comment, Long> {

    Comment getCommentById(long id);

    @Query("SELECT c.id, c.text FROM Comment c WHERE c.id = :id")
    String getTextCommentById(long id);

    @Query("select c.id from Comment c where c.author.id =:authorId and c.timeStamp = :timeStamp")
    long getCommentID(@Param("authorId") long authorId,
                      @Param("timeStamp") String timeStamp);

    @Query("SELECT c FROM Comment c")
    List<Comment> getAllComments();

    @Modifying
    @Query("UPDATE Comment c SET c.text = :text WHERE c.id = :id")
    void updateComment(@Param("text") String password, @Param("id") long id);


}
