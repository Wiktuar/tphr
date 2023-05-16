package ru.tphr.tphr.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private long id;
    private String text;
    private String timeStamp;

    @JsonProperty("author")
    private AuthorDTO authorDTO;
}
