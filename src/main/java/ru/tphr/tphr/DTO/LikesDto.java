package ru.tphr.tphr.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tphr.tphr.entities.security.Author;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LikesDto {
    private long id;
    private Set<Author> setOfLikes;
}
