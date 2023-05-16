package ru.tphr.tphr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tphr.tphr.entities.Comment;
import ru.tphr.tphr.repository.CommentRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    private CommentRepo commentRepo;

    @Autowired
    public void setCommentRepo(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

//  метод сохранения комментария в базу данных.
    public Comment saveComment(Comment comment){
        return commentRepo.save(comment);
    }

//  метод получения ID комментария из базы данных по метке времени и ID его автора
    public long getCommentId(Long authorId, String timeStamp){
        return commentRepo.getCommentID(authorId, timeStamp);
    }

//  получение всех комментриев
    public List<Comment> getAllComments(){
        return commentRepo.getAllComments();
    }

    public Comment getCommentById(long id){
        return commentRepo.getCommentById(id);
    }

//  метод получения текста комментария по его ID
    public String getTextCommentById(long id){
        return commentRepo.getTextCommentById(id);
    }

    @Transactional
    public void updateComment(String text, long id){
        commentRepo.updateComment(text, id);
    }

//  удаление комментария о ID
    public void deleteCommentById(Long id){
        commentRepo.deleteById(id);
    }
}
