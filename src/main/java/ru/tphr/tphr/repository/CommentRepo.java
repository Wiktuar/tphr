package ru.tphr.tphr.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tphr.tphr.DTO.CommentDTO;
import ru.tphr.tphr.entities.Comment;

import java.util.List;

@Repository
public interface CommentRepo extends CrudRepository<Comment, Long> {

    Comment getCommentById(long id);

    @Query("SELECT c FROM Comment c WHERE c.poemId = :poemId")
    List<Comment> getCommentsByPoemId(@Param("poemId") long poemId);

    @Query("SELECT new ru.tphr.tphr.DTO.CommentDTO(c.id, c.text) FROM Comment c WHERE c.id = :id")
    CommentDTO getTextCommentById(long id);

    @Query("select c.id from Comment c where c.author.id =:authorId and c.timeStamp = :timeStamp")
    long getCommentID(@Param("authorId") long authorId,
                      @Param("timeStamp") String timeStamp);

    @Query("SELECT c FROM Comment c")
    List<Comment> getAllComments();

//    @Query("select count(c.id) from Comment c where c.poemId = :id")
//    long getCountOfCommentsById(@Param("id") long id);

}
