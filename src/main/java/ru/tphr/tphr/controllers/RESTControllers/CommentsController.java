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
import ru.tphr.tphr.utils.ConvertEntityToDTO;
import ru.tphr.tphr.utils.Utils;

import java.security.Principal;
import java.util.List;

@RestController
public class CommentsController {
    private CommentService commentService;
    private AuthorService authorService;
    private ConvertEntityToDTO convertEntityToDTO;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setConvertEntityToDTO(ConvertEntityToDTO convertEntityToDTO) {
        this.convertEntityToDTO = convertEntityToDTO;
    }

//  метод получения всех комментариев
    @GetMapping("/getComments/{id}")
    public List<CommentDTO> getAllComments(@PathVariable long id){
        List<Comment> comments = commentService.getListOfCommentsByPoemId(id);
        List<CommentDTO> commentDtoes = convertEntityToDTO.convertList(comments, c -> convertEntityToDTO.convertToCommentDtoForList(c));
        return commentDtoes;
    }

//  метод сохранения комментария в БД
    @PostMapping("/addComment")
    public CommentDTO addComment(@ModelAttribute Comment comment,
                              Principal principal){
        comment.setTimeStamp(Utils.convertTimeToString());
        Author author = authorService.getAuthorByEmail(principal.getName());

        String[] massOfLines = comment.getText().split("\\n");;
        comment.setText(Utils.editPoem(massOfLines));
        comment.setAuthor(author);
        commentService.saveComment(comment);
        comment.setId(commentService.getCommentId(comment.getAuthor().getId(), comment.getTimeStamp()));;
        CommentDTO commentDTO = convertEntityToDTO.convertToCommentDTO(comment);
        commentDTO.setAuthorDTO(convertEntityToDTO.convertToAuthorDto(author));

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
        String[] massOfLines = text.split("\\n");;
        Comment comment = commentService.getCommentById(id);
        comment.setText(Utils.editPoem(massOfLines));
        comment = commentService.saveComment(comment);
        CommentDTO commentDTO = convertEntityToDTO.convertToCommentDTO(comment);
        commentDTO.setAuthorDTO(convertEntityToDTO.convertToAuthorDto(comment.getAuthor()));
        return commentDTO;
    }

//  метод получения ID и текста комментария в виде CommentDTO
    @GetMapping("/getcomment/{id}")
    public CommentDTO getCommentById(@PathVariable long id) throws JsonProcessingException {
        CommentDTO commentDTO = commentService.getTextCommentById(id);
        commentDTO.setText(Utils.editPoem(commentDTO.getText()));
        System.out.println(commentDTO.getText());
        return commentDTO;
    }
}
