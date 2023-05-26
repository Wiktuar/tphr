//https://sysout.ru/preobrazovanie-entity-v-dto-s-pomoshhyu-modelmapper/
package ru.tphr.tphr.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tphr.tphr.DTO.AuthorDTO;
import ru.tphr.tphr.DTO.CommentDTO;
import ru.tphr.tphr.DTO.PoemDTO;
import ru.tphr.tphr.entities.Comment;
import ru.tphr.tphr.entities.Poem;
import ru.tphr.tphr.entities.security.Author;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConvertEntityToDTO {

    private static ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//  метод, преобразующий Comment в CommentDTO
    public CommentDTO convertToCommentDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

//  метод, преобразующий Author в AuthorDTO
    public AuthorDTO convertToAuthorDto(Author author) {
        return modelMapper.map(author, AuthorDTO.class);
    }

//  метод, который будет использоваться в преобразовании списка записей
    public CommentDTO convertToCommentDtoForList(Comment comment) {
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        commentDTO.setAuthorDTO(convertToAuthorDto(comment.getAuthor()));
        return commentDTO;
    }

    //  метод преобразующий список сущностей в DTO. Он принимает список и функцию для конвертации.
    public <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(e -> converter.apply(e)).collect(Collectors.toList());
    }
}
