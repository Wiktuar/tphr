package ru.tphr.tphr.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditPoemDTO {
    private long id;
    private String header;
    private String content;
    private String fileName;
    private String releaseDate;

}
