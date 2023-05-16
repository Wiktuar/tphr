package ru.tphr.tphr.controllers.RESTControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tphr.tphr.DTO.CommentDTO;
import ru.tphr.tphr.entities.Comment;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.services.AuthorService;
import ru.tphr.tphr.services.CommentService;
import ru.tphr.tphr.utils.CoverEntityToDTO;
import ru.tphr.tphr.utils.Utils;

import java.security.Principal;
import java.util.List;

@RestController
public class CommentsController {
    private CommentService commentService;
    private AuthorService authorService;
    private CoverEntityToDTO coverEntityToDTO;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setCoverEntityToDTO(CoverEntityToDTO coverEntityToDTO) {
        this.coverEntityToDTO = coverEntityToDTO;
    }

//  метод получения всех комментариев
    @GetMapping("/getComments")
    public List<CommentDTO> getAllComments(){
        List<Comment> comments = commentService.getAllComments();
        List<CommentDTO> commntDtoes = coverEntityToDTO.convertList(comments, c -> coverEntityToDTO.convertToCommentDtoForList(c));
        return commntDtoes;
    }

//  метод сохранения комментария в БД
    @PostMapping("/addComment")
    public CommentDTO addComment(@RequestBody Comment comment,
                              Principal principal){
        comment.setTimeStamp(Utils.conbertTimetoStrimg());
        Author author = authorService.getAuthorByEmail(principal.getName());
        comment.setAuthor(author);
        commentService.saveComment(comment);
        comment.setId(commentService.getCommentId(comment.getAuthor().getId(), comment.getTimeStamp()));;
        CommentDTO commentDTO = coverEntityToDTO.convertToCommentDTO(comment);
        commentDTO.setAuthorDTO(coverEntityToDTO.convertToAuthorDto(author));

        return commentDTO;
    }

//  метод удаления комментария по ID
    @DeleteMapping("/deletecomment/{id}")
    public HttpStatus deleteCommentById(@PathVariable long id){
        commentService.deleteCommentById(id);
        return HttpStatus.OK;
    }

    @PostMapping("/editcomment/{id}")
    public CommentDTO updateComment(@RequestParam long id,
                                    @RequestParam String text){
        Comment comment = commentService.getCommentById(id);
        comment.setText(text);
        comment = commentService.saveComment(comment);
        CommentDTO commentDTO = coverEntityToDTO.convertToCommentDTO(comment);
        commentDTO.setAuthorDTO(coverEntityToDTO.convertToAuthorDto(comment.getAuthor()));
        System.out.println(commentDTO.getText());
        return commentDTO;
    }

//  метод получения ID и текста комментария в виде строки. Разделяется строка на Frontende
    @GetMapping("/getcomment/{id}")
    public String getCommentById(@PathVariable long id) throws JsonProcessingException {
        String comment = commentService.getTextCommentById(id);
        return new ObjectMapper().writeValueAsString(comment);
    }
}
